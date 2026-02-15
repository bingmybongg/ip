package alfred.storage;

import java.util.ArrayList;
import java.util.List;
import java.io.File;
import java.io.FileWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import alfred.task.Deadline;
import alfred.task.Event;
import alfred.task.Task;
import alfred.task.Todo;

/**
 * Manages persistent storage of tasks in CSV format.
 * <p>
 * Tasks are saved to a CSV file where each line represents one task with fields
 * separated by commas. Task descriptions and date fields are escaped if they
 * contain special characters.
 */
public class FileManager {
    private final Path file;

    /**
     * Creates a new file manager for the specified directory path.
     *
     * @param path the directory path where the task file will be stored
     * @throws IOException if an I/O error occurs creating the directory
     * @throws IllegalArgumentException if path is null or blank
     */
    public FileManager(String path) throws IOException {
        validatePath(path);
        Files.createDirectories(Paths.get(path));
        this.file = Paths.get(path + File.separator + "alfred.csv");
    }

    /**
     * Validates that the provided path is not null or blank.
     *
     * @param path the path to validate
     * @throws IllegalArgumentException if path is null or blank
     */
    private void validatePath(String path) {
        if (path == null || path.isBlank()) {
            throw new IllegalArgumentException("Path cannot be null or blank");
        }
    }

    /**
     * Loads tasks from the CSV file.
     *
     * @return a list of loaded tasks (never {@code null})
     * @throws IOException if an I/O error occurs reading the file
     */
    public ArrayList<Task> load() throws IOException {
        if (!Files.exists(this.file)) {
            return new ArrayList<>();
        }

        ArrayList<Task> tasks = new ArrayList<>();
        List<String> lines = Files.readAllLines(this.file);

        loadTasksFromLines(tasks, lines);

        return tasks;
    }

    /**
     * Loads tasks from CSV lines into the tasks list.
     *
     * @param tasks the list to add loaded tasks to
     * @param lines the CSV lines to parse
     */
    private void loadTasksFromLines(ArrayList<Task> tasks, List<String> lines) {
        for (int lineNum = 0; lineNum < lines.size(); lineNum++) {
            String line = lines.get(lineNum);
            loadTaskFromLine(tasks, line, lineNum);
        }
    }

    /**
     * Loads a single task from a CSV line.
     *
     * @param tasks the list to add the task to
     * @param line the CSV line to parse
     * @param lineNum the line number (for error reporting)
     */
    private void loadTaskFromLine(ArrayList<Task> tasks, String line, int lineNum) {
        try {
            String[] fields = parseCsvLine(line);

            if (fields.length < 3) {
                logMalformedLine(lineNum, line);
                return;
            }

            Task task = createTaskFromFields(fields, lineNum);
            if (task != null) {
                tasks.add(task);
            }
        } catch (Exception e) {
            logLoadError(lineNum, e);
        }
    }

    /**
     * Creates a task from parsed CSV fields.
     *
     * @param fields the parsed CSV fields
     * @param lineNum the line number (for error reporting)
     * @return the created task, or null if creation failed
     */
    private Task createTaskFromFields(String[] fields, int lineNum) {
        String type = fields[0];

        switch (type) {
        case "todo":
            return createTodoTask(fields);
        case "deadline":
            return createDeadlineTask(fields, lineNum);
        case "event":
            return createEventTask(fields, lineNum);
        default:
            logUnknownType(type, lineNum);
            return null;
        }
    }

    /**
     * Creates a Todo task from CSV fields.
     *
     * @param fields the parsed CSV fields
     * @return the created Todo task
     */
    private Task createTodoTask(String[] fields) {
        Task task = new Todo(fields[1]);
        return markTaskIfNeeded(task, fields[2]);
    }

    /**
     * Creates a Deadline task from CSV fields.
     *
     * @param fields the parsed CSV fields
     * @param lineNum the line number (for error reporting)
     * @return the created Deadline task, or null if fields are incomplete
     */
    private Task createDeadlineTask(String[] fields, int lineNum) {
        if (fields.length < 4) {
            logIncompleteDeadline(lineNum);
            return null;
        }
        Task task = new Deadline(fields[1], fields[3]);
        return markTaskIfNeeded(task, fields[2]);
    }

    /**
     * Creates an Event task from CSV fields.
     *
     * @param fields the parsed CSV fields
     * @param lineNum the line number (for error reporting)
     * @return the created Event task, or null if fields are incomplete
     */
    private Task createEventTask(String[] fields, int lineNum) {
        if (fields.length < 5) {
            logIncompleteEvent(lineNum);
            return null;
        }
        Task task = new Event(fields[1], fields[3], fields[4]);
        return markTaskIfNeeded(task, fields[2]);
    }

    /**
     * Marks a task if the marked field indicates it should be marked.
     *
     * @param task the task to potentially mark
     * @param markedField the marked field value ("1" or "0")
     * @return the task (marked if needed)
     */
    private Task markTaskIfNeeded(Task task, String markedField) {
        if (markedField.equals("1")) {
            return task.mark();
        }
        return task;
    }

    /**
     * Saves tasks to the CSV file atomically.
     *
     * @param tasks the list of tasks to save (must not be {@code null})
     * @throws IOException if an I/O error occurs writing the file
     * @throws NullPointerException if tasks is {@code null}
     */
    public void save(ArrayList<Task> tasks) throws IOException {
        validateTasksNotNull(tasks);

        Path tempFile = createTempFilePath();
        writeTasksToFile(tasks, tempFile);
        replaceFileAtomically(tempFile);
    }

    /**
     * Validates that the tasks list is not null.
     *
     * @param tasks the tasks list to validate
     * @throws NullPointerException if tasks is null
     */
    private void validateTasksNotNull(ArrayList<Task> tasks) {
        if (tasks == null) {
            throw new NullPointerException("Tasks list cannot be null");
        }
    }

    /**
     * Creates a temporary file path for atomic writing.
     *
     * @return the temporary file path
     */
    private Path createTempFilePath() {
        return Paths.get(this.file.toString() + ".tmp");
    }

    /**
     * Writes all tasks to the specified file.
     *
     * @param tasks the tasks to write
     * @param filePath the file path to write to
     * @throws IOException if an I/O error occurs
     */
    private void writeTasksToFile(ArrayList<Task> tasks, Path filePath) throws IOException {
        try (FileWriter fw = new FileWriter(filePath.toString(), false)) {
            for (Task task : tasks) {
                writeTaskToFile(task, fw);
            }
        }
    }

    /**
     * Writes a single task to the file writer.
     *
     * @param task the task to write
     * @param fw the file writer
     * @throws IOException if an I/O error occurs
     */
    private void writeTaskToFile(Task task, FileWriter fw) throws IOException {
        String type = task.type();

        switch (type) {
        case "todo":
            writeTodoTask(task, fw);
            break;
        case "deadline":
            writeDeadlineTask(task, fw);
            break;
        case "event":
            writeEventTask(task, fw);
            break;
        }
    }

    /**
     * Writes a Todo task in CSV format.
     *
     * @param task the task to write
     * @param fw the file writer
     * @throws IOException if an I/O error occurs
     */
    private void writeTodoTask(Task task, FileWriter fw) throws IOException {
        String line = buildTodoCsvLine(task);
        fw.write(line + "\n");
    }

    /**
     * Builds a CSV line for a Todo task.
     *
     * @param task the task
     * @return the CSV line
     */
    private String buildTodoCsvLine(Task task) {
        return "todo," + escapeCsv(task.getTask()) + "," + task.getMark();
    }

    /**
     * Writes a Deadline task in CSV format.
     *
     * @param task the task to write
     * @param fw the file writer
     * @throws IOException if an I/O error occurs
     */
    private void writeDeadlineTask(Task task, FileWriter fw) throws IOException {
        String line = buildDeadlineCsvLine(task);
        fw.write(line + "\n");
    }

    /**
     * Builds a CSV line for a Deadline task.
     *
     * @param task the task
     * @return the CSV line
     */
    private String buildDeadlineCsvLine(Task task) {
        Deadline deadline = (Deadline) task;
        return "deadline," +
                escapeCsv(task.getTask()) + "," +
                task.getMark() + "," +
                escapeCsv(deadline.getDeadline());
    }

    /**
     * Writes an Event task in CSV format.
     *
     * @param task the task to write
     * @param fw the file writer
     * @throws IOException if an I/O error occurs
     */
    private void writeEventTask(Task task, FileWriter fw) throws IOException {
        String line = buildEventCsvLine(task);
        fw.write(line + "\n");
    }

    /**
     * Builds a CSV line for an Event task.
     *
     * @param task the task
     * @return the CSV line
     */
    private String buildEventCsvLine(Task task) {
        Event event = (Event) task;
        return "event," +
                escapeCsv(task.getTask()) + "," +
                task.getMark() + "," +
                escapeCsv(event.getFrom()) + "," +
                escapeCsv(event.getTo());
    }

    /**
     * Replaces the target file with the temp file atomically.
     *
     * @param tempFile the temporary file path
     * @throws IOException if an I/O error occurs
     */
    private void replaceFileAtomically(Path tempFile) throws IOException {
        Files.deleteIfExists(this.file);
        Files.move(tempFile, this.file);
    }

    /**
     * Escapes a CSV field value if it contains special characters.
     *
     * @param value the value to escape
     * @return the escaped value wrapped in quotes if needed
     */
    private String escapeCsv(String value) {
        if (needsEscaping(value)) {
            return "\"" + value.replace("\"", "\"\"") + "\"";
        }
        return value;
    }

    /**
     * Checks if a value needs CSV escaping.
     *
     * @param value the value to check
     * @return true if escaping is needed
     */
    private boolean needsEscaping(String value) {
        return value.contains(",") || value.contains("\"") || value.contains("\n");
    }

    /**
     * Parses a CSV line handling quoted fields.
     *
     * @param line the CSV line to parse
     * @return array of field values
     */
    private String[] parseCsvLine(String line) {
        List<String> result = new ArrayList<>();
        parseCsvFields(line, result);
        return result.toArray(new String[0]);
    }

    /**
     * Parses CSV fields from a line into the result list.
     *
     * @param line the CSV line
     * @param result the list to add parsed fields to
     */
    private void parseCsvFields(String line, List<String> result) {
        boolean inQuotes = false;
        StringBuilder currentField = new StringBuilder();

        for (int i = 0; i < line.length(); i++) {
            char c = line.charAt(i);

            if (isEscapedQuote(c, i, line, inQuotes)) {
                currentField.append('"');
                i++; // Skip next quote
            } else if (c == '"') {
                inQuotes = !inQuotes;
                // Don't append the quote character itself - it's just a delimiter
            } else if (isFieldSeparator(c, inQuotes)) {
                result.add(currentField.toString());
                currentField = new StringBuilder();
            } else {
                currentField.append(c);
            }
        }

        result.add(currentField.toString());
    }

    /**
     * Checks if the current position is an escaped quote (two consecutive quotes).
     *
     * @param c the current character
     * @param i the current index
     * @param line the full line
     * @param inQuotes whether currently inside quotes
     * @return true if this is an escaped quote
     */
    private boolean isEscapedQuote(char c, int i, String line, boolean inQuotes) {
        return c == '"' && inQuotes && i + 1 < line.length() && line.charAt(i + 1) == '"';
    }

    /**
     * Checks if the current character is a field separator.
     *
     * @param c the current character
     * @param inQuotes whether currently inside quotes
     * @return true if this is a field separator
     */
    private boolean isFieldSeparator(char c, boolean inQuotes) {
        return c == ',' && !inQuotes;
    }

    /**
     * Logging helper methods for various warning scenarios during loading.
     */
    private void logMalformedLine(int lineNum, String line) {
        System.err.println("Warning: Skipping malformed line " + (lineNum + 1) + ": " + line);
    }

    /**
     * Logs an error that occurred while loading a task.
     *
     * @param lineNum the line number where the error occurred
     * @param e the exception that was thrown
     */
    private void logLoadError(int lineNum, Exception e) {
        System.err.println("Warning: Failed to load task at line " +
                (lineNum + 1) + ": " + e.getMessage());
    }

    /**
     * Logs a warning about an unknown task type encountered during loading.
     *
     * @param type the unknown task type
     * @param lineNum the line number where the unknown type was found
     */
    private void logUnknownType(String type, int lineNum) {
        System.err.println("Warning: Unknown task type '" + type +
                "' at line " + (lineNum + 1));
    }

    /**
     * Logs a warning about an incomplete deadline task encountered during loading.
     *
     * @param lineNum the line number where the incomplete deadline was found
     */
    private void logIncompleteDeadline(int lineNum) {
        System.err.println("Warning: Skipping incomplete deadline at line " + (lineNum + 1));
    }

    /**
     * Logs a warning about an incomplete event task encountered during loading.
     *
     * @param lineNum the line number where the incomplete event was found
     */
    private void logIncompleteEvent(int lineNum) {
        System.err.println("Warning: Skipping incomplete event at line " + (lineNum + 1));
    }
}