import products.ProductService;
import products.UserRecord;


import java.util.Scanner;

public class Main {

    //<メニューモードの定数>
    private static final int MODE_DISPLAY = 1;
    private static final int MODE_NEW = 2;
    private static final int MODE_DELETE = 3;
    private static final int MODE_EXIT = 9;

    static ProductService PS;

    public static void main(String[] args) {
        //<初期化処理と初期メニュー表示>
        start();


        while (true) {
            var sc = new Scanner(System.in);
            var input = sc.nextInt();

            if (input == MODE_DISPLAY) {
                //一覧表示モード
                displayUserList();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_NEW) {
                //追加モード
                addUser();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_DELETE) {
                //削除モード
                deleteUser();
                System.out.println("");
                displayMenu();
            } else if (input == MODE_EXIT) {
                //終了モード
                System.out.println("終了します。");
                break;
            } else {
                //無効な入力時の表示
                System.out.println("もう一度入力してください。");
            }
        }
    }

    //<メニューの表示>
    private static void displayMenu() {
        //メニュー内容を表示する
        System.out.println("メニューを選択してください。");
        System.out.println("１：一覧表示");
        System.out.println("２：新規追加");
        System.out.println("３：削除");
        System.out.println("９：終了");
    }

    //<初期化と初期表示>
    public static void start() {
        //インスタンス
        PS = new ProductService();

        System.out.println("---------------------------");
        System.out.println("ユーザー管理システム\n");
        //メニュー一覧を表示させ、待ち状態にする
        displayMenu();
    }

    //<ユーザーリストの表示>
    private static void displayUserList() {
        //ユーザーリストを表示する
        for (var user : PS.getUserData()) {
            System.out.println(user);
        }
    }

    //<ユーザーの追加>
    private static void addUser() {
        System.out.println("---------------------------");
        System.out.println("ユーザー追加");
        System.out.println("所属企業を選択してください。");

        //<会社リストの表示>
        //------------------------------------
        var sc = new Scanner(System.in);
        var companyId = sc.nextInt();
        //------------------------------------
        //Scannerで入力する（名前の入力）
        System.out.println("");
        System.out.println("名前を入力してください。");
        var sc2 = new Scanner(System.in);
        var name = sc2.next();
        //------------------------------------
        //スコアの入力
        System.out.println("");
        System.out.println("スコアを入力してください。");
        //int型だからScanner文がいらない？？
        var score = sc.nextInt();
        //-------------------------------------
        //新しいユーザーを作成してユーザーリストに追加
        var newUser = new UserRecord(0, name, companyId, score);
        PS.insert(newUser);
        //追加されたユーザー情報を表示
        System.out.println("以下の内容でユーザーが追加されました。");
        System.out.println("id：" + newUser.id());
        System.out.println("名前：" + newUser.name());
        System.out.println("所属企業ID：" + newUser.company_id());
        System.out.println("スコア：" + newUser.score());
    }

    //<ユーザーの削除>
    private static void deleteUser() {
        System.out.println("--------------------------");
        System.out.println("ユーザー削除");
        System.out.println("削除するユーザーのIDを入力してください");
        //ScannerでユーザーIDを入力する
        var sc = new Scanner(System.in);
        var id = sc.nextInt();

        // 削除されたユーザーの情報を取得
        var deletedUser = PS.findById(id);
        var deletedRows = PS.delete(id);

        if (deletedRows > 0) {
            // 削除されたユーザーの情報を取得

            if (deletedUser != null) {
                // 削除されたユーザーの情報を表示
                System.out.println("以下のユーザーを削除しました。");
                System.out.println("id：" + deletedUser.id());
                System.out.println("所属企業id：" + deletedUser.company_id());
                System.out.println("名前：" + deletedUser.name());
                System.out.println("スコア：" + deletedUser.score());
            } else {
                System.out.println("削除されたユーザーの情報が見つかりませんでした。");
            }
        } else {
            System.out.println("指定されたIDのユーザーは見つかりませんでした。");
        }
    }
}


















