import java.sql.*;
import java.util.List;
import java.util.Scanner;
import java.util.ArrayList;

public class Main {

    //<メニューモードの定数>
    private static final int MODE_DISPLAY = 1;
    private static final int MODE_NEW = 2;
    private static final int MODE_DELETE = 3;
    private static final int MODE_EXIT = 9;

    //<Companyクラスの定義>
    private record Company(int id,String name){}
    //Userクラスの定義
    private record User(int id, int CompanyId,String name,int score){}

    //<ユーザーリストと会社リストの初期化>
        //Userオブジェクトのリストを格納するための変数。（Userはレコードクラス持っているデータ）
    private static List<User> userList;
        //Companyオブジェクトのリストを格納するための変数。（Companyはレコードクラスで持っているデータ）
    private static List<Company> companyList;

    public static void main(String[] args) {
        //<初期化処理と初期メニュー表示>
        start();

        while (true){
             var sc = new Scanner(System.in);
             var input = sc.nextInt();

             if(input == MODE_DISPLAY){
                 //一覧表示モード
                 displayUserList();
                 System.out.println("");
                 displayMenu();
             }else if(input == MODE_NEW){
                 //追加モード
                 addUser();
                 System.out.println("");
                 displayMenu();
             }else if(input == MODE_DELETE){
                 //削除モード
                 deleteUser();
                 System.out.println("");
                 displayMenu();
             }else if(input == MODE_EXIT){
                 //終了モード
                 System.out.println("終了します。");
                 break;
             }else{
                 //無効な入力時の表示
                 System.out.println("もう一度入力してください。");
             }
        }
    }

    //<メニューの表示>
    private static void displayMenu(){
        //メニュー内容を表示する
        System.out.println("メニューを選択してください。");
        System.out.println("１：一覧表示");
        System.out.println("２：新規追加");
        System.out.println("３：削除");
        System.out.println("９：終了");
    }

    //<初期化と初期表示>
    public static void start() {
        //会社情報の初期化
        companyList = new ArrayList<>();
            //addメソッドを使って情報を追加できる
        companyList.add(new Company(1,"株式会社A"));
        companyList.add(new Company(2,"株式会社B"));
        //ユーザー情報の初期化
        userList = new ArrayList<>();
        userList.add(new User(1,1,"AAA",80));
        userList.add(new User(2,2,"BBB",90));

        System.out.println("---------------------------");
        System.out.println("ユーザー管理システム\n");
        //メニュー一覧を表示させ、待ち状態にする
        displayMenu();
    }
    //<ユーザーリストの表示>
    private static void displayUserList(){
        //ユーザーリストを表示する
        for(var user : userList){
            System.out.println(user);
        }
    }

    //<ユーザーの追加>
    private static void addUser(){
        System.out.println("---------------------------");
        System.out.println("ユーザー追加");
        System.out.println("所属企業を選択してください。");

        //<会社リストの表示>
     //------------------------------------
        //Scannerで入力する（会社IDの入力）
        for(var company : companyList) {
            System.out.println(company.id() + ":" + company.name());
        }
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
        //最大のIDを取得する(理解低！)
        //ストリーム化し、userListの中のIDをint型で取り、取ってきたidの最大をMaxIdに入れる
        var maxId = userList.stream().mapToInt(User::id).max().orElse(0);
        //新しいユーザーを作成してユーザーリストに追加
        var newUser = new User(maxId + 1, companyId, name, score);
        userList.add(newUser);
        //追加されたユーザー情報を表示
        System.out.println("以下の内容でユーザーが追加されました。");
        System.out.println("id：" + newUser.id());
        System.out.println("所属企業ID：" + newUser.CompanyId());
        System.out.println("名前：" + newUser.name());
        System.out.println("スコア：" + newUser.score());
        }

        //<ユーザーの削除>
        private static void deleteUser(){
            System.out.println("--------------------------");
            System.out.println("ユーザー削除");
            System.out.println("削除するユーザーのIDを入力してください");
            //ScannerでユーザーIDを入力する
            var sc = new Scanner(System.in);
            var id = sc.nextInt();
            //指定されたIDのユーザーを検索して削除(理解低！)
            //ストリーム化し入力したidがtrueになる条件を書いてフィルターを掛ける、その中の最初にとれたidを持ち、getで要素に変換する。
            var targetUser = userList.stream().filter(user -> user.id() == id).findFirst().get();
            userList.remove(targetUser);

            //削除されたユーザーの情報を表示
            System.out.println("以下のユーザーを削除しました。");
            System.out.println("id：" + targetUser.id());
            System.out.println("所属企業id：" + targetUser.id());
            System.out.println("名前：" + targetUser.name());
            System.out.println("スコア：" + targetUser.score());
    }
}