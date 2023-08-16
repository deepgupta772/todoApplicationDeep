import java.util.*;

class Task {
    private String description;
    private int priority;

    public Task(String description, int priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public int getPriority() {
        return priority;
    }

    public void setPriority(int priority) {
        this.priority = priority;
    }
    public void setDescription(String description){
        this.description = description;
    }
}

class User {
    private String username;
    private String password;
    private List<Task> tasks;

    public User(String username, String password) {
        this.username = username;
        this.password = password;
        this.tasks = new ArrayList<>();
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public List<Task> getTasks() {
        return tasks;
    }
}

public class ToDoAppy {
    private static Map<String, User> users = new HashMap<>();
    private static User currentUser = null;

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("Welcome to the ToDo App!");

            if (currentUser == null) {
                System.out.println("1. Register");
                System.out.println("2. Login");
                System.out.println("3. Exit");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        registerUser(scanner);
                        break;
                    case 2:
                        loginUser(scanner);
                        break;
                    case 3:
                        System.out.println("Goodbye!");
                        return;
                    default:
                        System.out.println("Invalid choice.");
                }
            } else {
                System.out.println("Hello, " + currentUser.getUsername() + "!");
                System.out.println("1. Add Task");
                System.out.println("2. Remove Task");
                System.out.println("3. Show Tasks");
                System.out.println("4. Edit Task");
                System.out.println("5. Delete Task");
                System.out.println("6. Clear All / Completed Tasks");
                System.out.println("7. Logout");

                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume the newline

                switch (choice) {
                    case 1:
                        addTask(scanner);
                        break;
                    case 2:
                        removeTask(scanner);
                        break;
                    case 3:
                        showTasks();
                        break;
                    case 4:
                        editTask(scanner);
                        break;
                    case 5:
                        deleteTask(scanner);
                        break;
                    case 6:
                        clearTasks();
                        break;
                    case 7:
                        currentUser = null;
                        break;
                    default:
                        System.out.println("Invalid choice.");
                }
            }
        }
    }

    private static void registerUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            System.out.println("Username already exists.");
        } else {
            users.put(username, new User(username, password));
            System.out.println("User registered successfully.");
        }
    }

    private static void loginUser(Scanner scanner) {
        System.out.print("Enter username: ");
        String username = scanner.nextLine();
        System.out.print("Enter password: ");
        String password = scanner.nextLine();

        if (users.containsKey(username)) {
            User user = users.get(username);
            if (user.getPassword().equals(password)) {
                currentUser = user;
                System.out.println("Login successful.");
            } else {
                System.out.println("Incorrect password.");
            }
        } else {
            System.out.println("Username not found.");
        }
    }

    private static void addTask(Scanner scanner) {
        System.out.print("Enter task description: ");
        String description = scanner.nextLine();

        System.out.print("Enter task priority (1-5, where 5 is highest): ");
        int priority = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        if (priority < 1 || priority > 5) {
            System.out.println("Invalid priority. Using default priority 3.");
            priority = 3;
        }

        currentUser.getTasks().add(new Task(description, priority));
        System.out.println("Task added successfully.");
    }

    private static void removeTask(Scanner scanner) {
        System.out.print("Enter task index to remove: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Task> tasks = currentUser.getTasks();
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task removed successfully.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    private static void showTasks() {
        List<Task> tasks = currentUser.getTasks();
        if (tasks.isEmpty()) {
            System.out.println("No tasks to display.");
        } else {
            tasks.sort(Comparator.comparingInt(Task::getPriority).reversed());

            System.out.println("Tasks:");
            for (int i = 0; i < tasks.size(); i++) {
                Task task = tasks.get(i);
                System.out.println((i + 1) + ". Priority: " + task.getPriority() + " - " + task.getDescription());
            }
        }
    }

    private static void editTask(Scanner scanner) {
        System.out.print("Enter task index to edit: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Task> tasks = currentUser.getTasks();
        if (index >= 0 && index < tasks.size()) {
            System.out.print("Enter new task description: ");
            String newDescription = scanner.nextLine();
            tasks.get(index).setDescription(newDescription);
            System.out.println("Task edited successfully.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    private static void deleteTask(Scanner scanner) {
        System.out.print("Enter task index to delete: ");
        int index = scanner.nextInt();
        scanner.nextLine(); // Consume the newline

        List<Task> tasks = currentUser.getTasks();
        if (index >= 0 && index < tasks.size()) {
            tasks.remove(index);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Invalid task index.");
        }
    }

    private static void clearTasks() {
        List<Task> tasks = currentUser.getTasks();
        tasks.clear();
        System.out.println("All tasks cleared successfully.");
    }
}
