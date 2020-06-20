import javafx.geometry.Pos;

import java.util.*;
public class UniLink {
    //�洢���еķ�����Ϣ
    private HashMap<String,Post> all=new HashMap<>();
    static Scanner sc=new Scanner(System.in);
    //��¼�������͵�����ĳ��ִ���(�����Լ����post���б��)
    public static HashMap<String,Integer> map=new HashMap();
    //�������ֹ��췽��
    UniLink(){
        init();
    }
    UniLink(HashMap<String,Post> all){
        this.all=all;

        //��ʼ��map
        for(String s:all.keySet()){
            s=s.replace("0","");
            String type=s.substring(0,3);
            if(map.containsKey(type)){
                int count=map.get(type)+1;
                map.put(type,count);
            }
            else
                map.put(type,1);
        }
        init();
    }

    //����ڵ�¼�˵���ѧ����ҳ�˵�ѡ����ʱ�������Ƿ���Ϲ淶��
    public boolean checkinput(String str,String menuType) {
        //���ַ������Ϊ�ַ�
        char[] input = str.toCharArray();
        if (menuType.equals("login"))
            if (input.length == 1 && input[0] - '0' > 0 && input[0] - '0' < 3)
                return true;

        if (menuType.equals("student"))
            if (input.length == 1 && input[0] - '0' > 0 && input[0] - '0' < 10)
                return true;
        return false;
    }

    public void init(){
        String str="";
        int op=0;
        loginMenu();
        str=sc.nextLine();
        if(checkinput(str,"login"))
            op=Integer.valueOf(str);
        while (op!=2) {
            switch (op) {
                case 1:
                    System.out.print("Enter username:");
                    String username=sc.next();
                    System.out.println("Welcome " +username+"!");
                    stuChoice(username);
                    break;

                case 2:
                    System.exit(0);
                default:
                    System.out.println("Please enter either 1 or 2!");
                    break;
            }
            loginMenu();
            str=sc.nextLine();
            if(checkinput(str,"login"))
                op=Integer.valueOf(str);

        }
    }

    public HashMap<String,Post>  getAll() {
        return all;
    }

    public void setAll(HashMap<String, Post> all) {
        this.all = all;
    }


    //��¼����
    public void loginMenu(){
        System.out.print(
                "** UniLink System ** \n" +
                        "1. Log in \n" +
                        "2. Quit\n "+
                        "Enter your choice:");
    }
    //ѧ���˵����棨�����棩
    public void studentMenu(){
        System.out.print(
                "** Student Menu **\n"+
                        "1. New Event Post\n" +
                        "2. New Sale Post\n" +
                        "3. New Job Post \n" +
                        "4. Reply To Post \n" +
                        "5. Display My Posts \n" +
                        "6. Display All Posts\n" +
                        "7. Close Post \n" +
                        "8. Delete Post \n" +
                        "9. Log Out \n" +
                        "Enter your choice:"
        );
    }

    //1-3:Adding a new Event/Sale/Job post
    public void addPost(String username,String type){
        //��������event�¼����
        String id="";
        //�ж��Ѿ���������Ϣ���Ƿ����Event
        if(map.containsKey(type)){
            //�������ȡevent�¼���������Ȼ��+1
            int num=map.get(type)+1;
            //����eventһ�����
            if(num>10 && num<100)
                id+=type+"0"+num;
            else if(num>100)
                id+=type+num;
            else
                id+=type+"00"+num;
            map.put(type,num);
        }
        else {
            id = type + "001";
            map.put(type,1);
        }


        System.out.println("Enter details of the event below:");
        System.out.print("Name: ");

        String title=sc.nextLine();
        System.out.print("Description: ");
        String description=sc.nextLine();
        if(title.isEmpty() || description.isEmpty()){
            System.out.println(" User cannot press the Enter key to skip a post name or description!");
            return;
        }

        int flag=0;
        if(type.equals("EVE")) {
            System.out.print("Venue:");
            String venue = sc.nextLine();
            String date="";
            int capacity=0;
            //���������Լ���ʽ���
            while (flag!=1) {
                System.out.print("Date:");
                date = sc.nextLine();

                try {
                    if(date.toCharArray()[2]=='/' && date.toCharArray()[5]=='/') {
                        String[] split=date.split("/");//����������ڰ�"/"�ָ������ÿ���ֵ�Ԫ���Ƿ������ʽҪ��
                        if(split[0].length()==2 && split[1].length()==2 && split[2].length()==4 )
                            flag = 1;
                        else
                            System.out.println("Date in an event post must be in the format dd/mm/yyyy ");
                    }
                    else
                        System.out.println("Date in an event post must be in the format dd/mm/yyyy ");
                } catch (Exception e) {
                    System.out.println("Date in an event post must be in the format dd/mm/yyyy ");
                }
            }

            flag=0;
            //���������Լ���ʽ���
            while (flag!=1) {
                System.out.print("Capacity:");
                capacity = sc.nextInt();
                if(capacity>0)
                    flag=1;
                else
                    System.out.println("You can only enter a positive number");
            }

            Event event=new Event(id,title,description,username,venue,date,capacity);
            HashMap<String,Post> temp=getAll();
            temp.put(id,event);
            setAll(temp);
            System.out.println("Success! Your event has been created with id "+id);

        }
        else if(type.equals("SAL")){
            int askingPrice=0,minimumRaise=0;

            //���������Ʒ�������Լ��۸񣬸�ʽ���
            while (flag!=1) {
                System.out.print("Asking Price:");
                askingPrice = sc.nextInt();
                System.out.print("Minimum Raise:");
                minimumRaise = sc.nextInt();

                if(askingPrice >0 && minimumRaise>0)
                    flag=1;
                else
                    System.out.println("You can only enter a positive number!");
            }

            Sale sale=new Sale(id,title,description,username,askingPrice,minimumRaise);
            HashMap<String,Post> temp=getAll();
            temp.put(id,sale);
            setAll(temp);
            System.out.println("Success! Your new sale has been created with id "+id);


        }
        else if(type.equals("JOB")){
            int proposedPrice=0;

            while (flag!=1) {
                System.out.print("Proposed Price:");
                proposedPrice = sc.nextInt();
                if(proposedPrice>0)
                    flag=1;
                else
                    System.out.println("You can only enter a positive number!");
            }
            Job job=new Job(id,title,description,username,proposedPrice);

            HashMap<String,Post> temp=getAll();
            temp.put(id,job);
            setAll(temp);
            System.out.println("Success! Your job has been created with id "+id);

        }
        sc.nextLine();
    }

    //4.Reply to a post
    public  void replyPost(String username){
        System.out.print("Enter post id or 'Q' to quit:");
        String postid=sc.next();

        if(postid.equals("Q")) {
            sc.nextLine();
            return;
        }

        //��������postid�Ƿ����
        while (!getAll().containsKey(postid)){
            System.out.println("The post id "+postid+" dosen't exist!");
            System.out.print("Enter post id or 'Q' to quit:");
            postid=sc.next();
            if(postid.equals("Q")) {
                sc.nextLine();
                return;
            }
        }

        //��ȡ����ı�ŵ�ǰ������ĸ���ж�����
        String type=postid.substring(0,3);
        //��ȡpostid��Ӧ��post
        Post temp=all.get(postid);

        //�жϵ�ǰpost�Ƿ�ر�
        if(temp.getStatus().equals("CLOSED")){
            System.out.println("The post is already closed");
            return;
        }

        //�жϵ�ǰ�û��Ƿ��Ѳ��룬���߷����߲���
        if(temp.getCreatorID().equals(username)) {
            System.out.println(" You are not allowed to reply to your own posts !");
            return;
        }
        if(temp.getReplies().contains(username)){
            System.out.println(" You have already replied to the post ");
            return;
        }


        int flag=0;
        if(type.equals("JOB")){
            Job job=(Job)temp;
            System.out.println("Name:"+job.getTitle());
            System.out.println("Proposed price:"+job.getProposedPrice());
            System.out.println("Lowest offer:"+job.getLowestOffer());

            String input="";
            double newprice=0;
            //�ж�����ľ����Ƿ���ϱ�׼��ӦС����ͳ��ۣ�
            while (flag!=1) {
                System.out.print("Enter your offer or 'Q' to quit :");
                input=sc.next();
                if(input.equals("Q")) {
                    sc.nextLine();
                    return;
                }

                newprice = Double.valueOf(input);
                if (newprice < 0)
                    System.out.println("Please enter a positive number!");
                else if (newprice >= job.getLowestOffer())
                    System.out.println("Offer rejected! Your offer price is greater than or equal to the current lowest offer");
                else
                    flag=1;
            }


            Reply reply=new Reply(postid,newprice,username);
            //����ظ����Ϲ淶����ӽ���job�е�replies
            if(job.handleReply(reply)) {
                HashMap update=getAll();
                update.put(postid,job);
                setAll(update);
                System.out.println("Offer accepted! ");
            }
            else {
                System.out.println("Sorry!This JOB is CLOSED");
                sc.nextLine();
                return;
            }

        }

        if(type.equals("SAL")){
            Sale sale=(Sale)temp;
            System.out.println("Name:"+sale.getTitle());
            System.out.println("Highest offer:"+sale.getHigestOffer());
            System.out.println("Minimum raise:"+sale.getMinimumRaise());

            String input="";
            double newprice=0;
            //�ж�����ľ����Ƿ���ϱ�׼��ӦС����ͳ��ۣ�
            while (flag!=1) {
                System.out.print("Enter your offer or 'Q' to quit:");
                input=sc.next();
                if(input.equals("Q")) {
                    sc.nextLine();
                    return;
                }

                newprice = Double.valueOf(input);
                if (newprice < 0)
                    System.out.println("Please enter a positive number!");
                else if (newprice <= sale.getHigestOffer())
                    System.out.println("Offer rejected! Your offer price is less than or equal to the current highest offer");
                else
                    flag=1;
            }

            Reply reply=new Reply(postid,newprice,username);
            //����ظ����Ϲ淶����ӽ���job�е�replies
            if(sale.handleReply(reply)) {
                HashMap update=getAll();
                update.put(postid,sale);
                setAll(update);
                System.out.println("Offer accepted! ");
            }
            else
                System.out.println("Sorry!This SALE is CLOSED");
        }

        if(type.equals("EVE")){
            Event event=(Event)temp;
            System.out.println("Name:"+event.getTitle());
            System.out.println("Venue:"+event.getVenue());
            System.out.println("Date:"+event.getDate());
            System.out.println("Capacity:"+event.getCapacity());
            System.out.println("Attendee count:"+event.getAttendeeCount());
            System.out.print("Enter 1 to join or 'Q' to quit ");
            String input=sc.next();

            if(input.equals("Q"))
                return;
            Reply reply=new Reply(postid,Double.valueOf(input),username);
            if(input.equals("1")) {
                if(event.handleReply(reply)) {
                    HashMap update=getAll();
                    update.put(postid,event);
                    setAll(update);
                    System.out.println("Offer accepted! ");
                }
                else
                    System.out.println("Sorry!This EVENT is CLOSED");

            }
            else
                System.out.println("Please enter 1  to join or 'Q' to quit");
        }
        sc.nextLine();
    }


    // 5.Display My Posts
    public void myPosts(String username){
        System.out.println("** My POSTS **");
        for(Post p:getAll().values()){
            if(p.getCreatorID().equals(username)){
                System.out.println(p.getReplyDetails());
                System.out.println("----------------------");
            }
        }
    }

    // 6.Display All Posts
    public void allPosts(String username){
        System.out.println("** All POSTS **");
        for(Post p:getAll().values()){
            System.out.println(p.getPostDetails());
            System.out.println("---------------------------");
        }
    }

    //7-8.Close and Delete Post
    public void cdPost(String username,String type){
        int flag=0;

        while (flag!=1){
            System.out.println("Please enter the post id you want to "+type+":");
            String id=sc.nextLine();
            if(getAll().containsKey(id) && getAll().get(id).getCreatorID().equals(username)){

                //ȷ�����루Y or N �����ִ�Сд��
                System.out.println("Are you sure to "+type+" "+id+" ?[Y/N]");
                String sure=sc.next();
                if (sure.equals("N") || sure.equals("n"))
                    return;
                if(sure.equals("Y")||sure.equals("y"));
                else{
                    while (true){
                        System.out.println("Are you sure to "+type+" "+id+" ?[Y/N]");
                        sure=sc.next();
                        if(sure.equals("y") || sure.equals("Y"))
                            break;
                    }
                }


                HashMap<String,Post> temp=getAll();
                if(type.equals("close"))
                    temp.get(id).setStatus("CLOSED");
                else if(type.equals("delete"))
                    temp.remove(id);
                setAll(temp);
                System.out.println("Finished!");
                sc.nextLine();
                flag=1;
            }
            else {
                System.out.println("The post id " + id + " is not exist in your posts!");
                return;
            }
        }
    }

    public void stuChoice(String username){
        String str="";
        int choice=0;
        studentMenu();
        sc.nextLine();
        str=sc.nextLine();
        if(checkinput(str,"student"))
            choice=Integer.valueOf(str);
        else
            System.out.println("Please enter number from 1-9");

        while (choice!=9){
            switch (choice){
                case 1:
                    addPost(username,"EVE");
                    break;
                case 2:
                    addPost(username,"SAL");
                    break;
                case 3:
                    addPost(username,"JOB");
                    break;
                case 4:
                    replyPost(username);
                    break;
                case 5:
                    myPosts(username);
                    break;
                case 6:
                    allPosts(username);
                    break;
                case 7:
                    cdPost(username,"close");
                    break;
                case 8:
                    cdPost(username,"delete");
                    break;
                case 9:
                    System.out.println("You hava successfully logged out");
                    break;
                default:
                    break;

            }
            studentMenu();
            str=sc.nextLine();
            if(checkinput(str,"student"))
                choice=Integer.valueOf(str);
            else
                System.out.println("Please enter number from 1-9\n");
        }
    }
}
