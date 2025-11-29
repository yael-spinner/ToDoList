//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
import java.util.List;
import java.util.Scanner;

public class Main {

    private static TaskService taskService;
    private static final Scanner scanner=new Scanner(System.in);

    public static void main(String[] args) {
        TaskRepository repository=new TaskRepository();
        taskService=new TaskService(repository);
        System.out.println("✨ ברוך הבא למערכת ניהול המשימות (Todo List) ✨");
        runMenuLoop();

        scanner.close();
        System.out.println("👋 המערכת נסגרה. הנתונים נשמרו בקובץ tasks.data.");


    }
    private static void runMenuLoop(){
        int choice;
        do{
            displayMenu();
            if (scanner.hasNextInt()) {
                choice= scanner.nextInt();
                scanner.nextLine();
                handleChoice(choice);
            }else {
                System.out.println("❌ קלט לא חוקי. אנא בחר מספר מהתפריט.");
                scanner.nextLine();
                choice = -1;
            }
        } while (choice !=0);
    }
    private static void displayMenu() {
        System.out.println("\n--- תפריט ראשי ---");
        System.out.println("1. ➕ הוספת משימה חדשה");
        System.out.println("2. 📝 סימון משימה כבוצעה (DONE)");
        System.out.println("3. 🔎 חפש משימות לפי טקסט (Title / Description)");
        System.out.println("4. 📋 הצג את כל המשימות (ממוינות לפי סטטוס)");
        System.out.println("5. 🗑️ מחיקת משימה");
        System.out.println("0. 🚪 יציאה ושמירה");
        System.out.print("בחר אפשרות: ");
    }
    private static void handleChoice(int choice) {
        switch (choice) {
            case 1: addTask(); break;
            case 2: markDone(); break;
            case 3: searchTasks(); break;
            case 4: displayAllTasks(); break;
            case 5: deleteTask(); break;
            case 0: break;
            default: System.out.println("אפשרות לא קיימת.");
        }
    }
    private static void addTask() {
        System.out.print("הכנס כותרת: ");
        String title = scanner.nextLine();
        System.out.print("הכנס תיאור: ");
        String description = scanner.nextLine();

        Task newTask = taskService.createNewTask(title, description);
        System.out.println("✅ משימה חדשה נוספה! ID: " + newTask.getId());
    }
    private static void markDone() {
        System.out.print("הכנס ID של המשימה לסימון כ-DONE: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();
            Task updatedTask = taskService.markTaskAsDone(id);
            if (updatedTask != null) {
                System.out.println("✅ משימה ID " + id + " סומנה כ-DONE.");
            } else {
                System.out.println("❌ משימה ID " + id + " לא נמצאה או שכבר סומנה.");
            }
        } else {
            System.out.println("❌ ID לא חוקי.");
            scanner.nextLine();
        }
    }
    private static void searchTasks() {
        System.out.print("הכנס טקסט לחיפוש: ");
        String searchText = scanner.nextLine();
        List<Task> results = taskService.searchTasks(searchText);

        if (results.isEmpty()) {
            System.out.println("🔍 לא נמצאו משימות תואמות לחיפוש '" + searchText + "'.");
        } else {
            System.out.println("--- תוצאות חיפוש (" + results.size() + " נמצאו) ---");
            results.forEach(System.out::println);
        }
    }
    private static void displayAllTasks() {
        List<Task> sortedTasks = taskService.getTasksSortedByStatus();
        System.out.println("--- כל המשימות (ממוינות לפי סטטוס) ---");
        if (sortedTasks.isEmpty()) {
            System.out.println("הרשימה ריקה.");
        } else {
            sortedTasks.forEach(task ->
                    System.out.println("[" + task.getStatus() + "] ID: " + task.getId() + " | " + task.getTitle())
            );
        }
    }
    private static void deleteTask() {
        System.out.print("הכנס ID של המשימה למחיקה: ");
        if (scanner.hasNextInt()) {
            int id = scanner.nextInt();
            scanner.nextLine();

            // שימוש ב-Repository דרך ה-Service
            if (taskService.getTaskRepository().delete(id)) {
                System.out.println("✅ משימה ID " + id + " נמחקה.");
            } else {
                System.out.println("❌ משימה ID " + id + " לא נמצאה.");
            }
        } else {
            System.out.println("❌ ID לא חוקי.");
            scanner.nextLine();
        }
    }
}