# Alfred Project Template

> This is a project template for a greenfield Java project. It's named after the Java mascot Duke.

Alfred is a command-line task manager that helps you track todos, deadlines, and events with simple, intuitive commands. Like Batman's loyal butler, Alfred is always ready to help you stay organized.

## ✨ Features

- 📝 **Three Task Types** - Todos, deadlines, and events to cover all your needs
- 🔍 **Smart Search** - Find tasks instantly with keyword search
- ✅ **Progress Tracking** - Mark tasks as complete or incomplete
- 💾 **Auto-Save** - Your data is automatically saved after every command
- 🎯 **Simple Commands** - Intuitive syntax that's easy to learn
- ❓ **Built-in Help** - Type `help` anytime to see all commands

## 🚀 Quick Start

1. **Prerequisites**: Ensure you have Java 11 or above installed
```bash
   java -version
```

2. **Download**: Get the latest `alfred.jar` from [releases](https://github.com/bingmybongg/ip/releases)

3. **Run**:
```bash
   java -jar alfred.jar
```

4. **Start managing tasks**:
```bash
   todo Buy groceries
   deadline Submit report /by 2026-03-15 1800
   list
```

That's it! Type `help` to see all available commands.

## 📖 Usage

### Adding Tasks
```bash
# Add a simple todo
todo Clean the Batmobile

# Add a task with a deadline
deadline Submit assignment /by 2026-03-15 2359

# Add an event with start and end times
event Team meeting /from 2026-03-20 1400 /to 2026-03-20 1600
```

### Managing Tasks
```bash
# View all tasks
list

# Search for tasks
find meeting

# Mark task as done (use task number from list)
mark 1

# Unmark task
unmark 1

# Delete task
delete 2

# Get help
help

# Exit and save
bye
```

## 📚 Documentation

**[📖 Full User Guide](jhttps://bingmybongg.github.io/ip/)** - Complete documentation with screenshots and examples

Quick links:
- [Command Reference](jhttps://bingmybongg.github.io/ip/#command-summary)
- [Error Messages](jhttps://bingmybongg.github.io/ip/#error-messages)
- [FAQ](jhttps://bingmybongg.github.io/ip/#faq)
- [Troubleshooting](jhttps://bingmybongg.github.io/ip/#troubleshooting)

## 🎯 Upcoming Features

We're constantly improving Alfred! Here's what's coming next:

- 🔄 **Command History** - Use ↑ arrow key to recall previously typed commands
- 🧹 **Clear Screen** - `clear` command to keep your interface neat and tidy
- 📅 **Flexible Date Formats** - Support for more intuitive date/time inputs (e.g., "tomorrow", "next Monday")
- 🎨 **Themes** - Customize Alfred's appearance
- 📊 **Statistics** - View your productivity trends
- 🔔 **Reminders** - Get notified about upcoming deadlines

Want to request a feature? [Open an issue](https://github.com/bingmybongg/ip/issues)!

## 🛠️ Built With

- **Java 17** - Core language
- **JUnit 5** - Testing framework
- **Gradle** - Build automation

## 📁 Project Structure
```
src/
├── main/
│   └── java/
│       └── alfred/
│           ├── command/      # Command pattern implementations
│           ├── parser/       # Input parsing logic
│           ├── storage/      # File I/O operations
│           ├── task/         # Task models and list management
│           └── ui/           # User interface components
└── test/
    └── java/
        └── alfred/           # Unit and integration tests
```

## 📋 Requirements

- Java 17 or higher
- Minimum 5MB free disk space
- Terminal/Command Prompt access

## 🙏 Acknowledgements

- **CS2103T Teaching Team** - For guidance and project requirements
- **Batman** - For the inspiration behind Alfred's name

---

<p align="center">
  Made with ☕ and 🦇 for CS2103T
</p>

<p align="center">
  <a href="https://bingmybongg.github.io/ip">Documentation</a> •
  <a href="https://github.com/bingmybongg/ip/releases">Download</a> 
</p>