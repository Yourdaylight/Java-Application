import java.util.HashMap;

public class Start_up {

    public static void main(String[] args){
        new UniLink(init());
    }

    public static HashMap init(){
        HashMap<String,Post> map=new HashMap<>();
        //初始化一些学生（username）
        String[] stu={"s1","s2","s3","s4","s5"};
        //初始化事件名称
        String[] posts={"EVE001","EVE002","SAL001","JOB001"};

        //初始化一个event(EVE001)
        String title1="Programming Study Group Wednesday night";
        String description1="Let's meet this Wednesday afternoon to finish the programming assignment1!";
        String venue1=" RMIT Library ";
        String date1="15/04/2025";
        int capacity1=5;
        Event event1=new Event(posts[0],title1,description1,stu[0],venue1,date1,capacity1);

        //初始化一个event(EVE001)
        String title2="Programming Study Group Friday Morning";
        String description2="Let's meet this Friday Morning to finish the programming assignment2!";
        String venue2=" RMIT Library ";
        String date2="17/04/2025";
        int capacity2=5;
        Event event2=new Event(posts[1],title2,description2,stu[0],venue2,date2,capacity2);

        //初始化一个sale(SAL001)
        String title3=" IPad Air 2 64GB ";
        String description3="Excellent working condition, comes with box, cable and charger";
        double Minraise=15;
        double Askprice=300;
        Sale sale1=new Sale(posts[2],title3,description3,stu[1],Askprice,Minraise);

        //初始化一个job(JOB001)
        String title4=" Moving House";
        String description4="Need a person to help me move my belongings to a  new house ";
        double Proposedprice=190;
        Job job1=new Job(posts[3],title4,description4,stu[2],Proposedprice);


        //初始化一些回复
        Reply reply1=new Reply(posts[0],1,stu[1]);
        Reply reply2=new Reply(posts[0],1,stu[2]);
        Reply reply3=new Reply(posts[0],1,stu[3]);
        Reply reply4=new Reply(posts[0],1,stu[4]);
        Reply reply6=new Reply(posts[2],100,stu[0]);
        Reply reply7=new Reply(posts[2],150,stu[2]);
        Reply reply8=new Reply(posts[3],200,stu[1]);
        Reply reply9=new Reply(posts[3],180,stu[3]);

        //将回复加入到事件中去
        event1.handleReply(reply1);
        event1.handleReply(reply2);
        event1.handleReply(reply3);
        event1.handleReply(reply4);
        sale1.handleReply(reply6);
        sale1.handleReply(reply7);
        job1.handleReply(reply8);
        job1.handleReply(reply9);

        //将事件加入到map中去
        map.put(posts[0],event1);
        map.put(posts[1],event2);
        map.put(posts[2],sale1);
        map.put(posts[3],job1);

        return map;

    }
}
