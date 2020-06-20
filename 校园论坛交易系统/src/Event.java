import sun.invoke.empty.Empty;

import java.util.ArrayList;

public class Event extends Post {
    private String Venue;
    private String Date;// dd/mm/yyyy
    private int Capacity;// the maximum number of attendenees of the event
    private int AttendeeCount;//the current number of participants in the event

    Event(String Id, String Title, String Description, String CreatorID,String venue,String date,int capacity)
    {
        super(Id, Title, Description, CreatorID);
        this.Venue=venue;
        this.Date=date;
        this.Capacity=capacity;
        this.AttendeeCount=0;
    }

    public String getVenue(){return this.Venue;}
    public String getDate(){return this.Date;}
    public int getCapacity(){return this.Capacity;}
    public int getAttendeeCount(){return this.AttendeeCount;}

    public void setVenue(String venue){this.Venue=venue;}
    public void setDate(String date){this.Date=date;}
    public void setCapacity(int capacity){this.Capacity=capacity;}
    public void setAttendeeCount(int attendeeCount){this.AttendeeCount=attendeeCount;}

    @Override
    public String getPostDetails(){
        String str= "ID:              "+getId()+"\n"+
                    "Title:           "+getTitle()+"\n"+
                    "Description:     "+getDescription()+"\n"+
                    "Creator ID:      "+getCreatorID()+"\n"+
                    "Status:          "+getStatus()+"\n"+
                    "Venue:           "+this.Venue+"\n"+
                    "Date:            "+this.Date+"\n"+
                    "Capacity:        "+this.Capacity+"\n"+
                    "Attendees:       "+this.AttendeeCount+"\n";

        return str;
    }

    @Override
    public boolean handleReply(Reply reply) {
        /* the event is not full and the student id is not yet recorded in the event,
        add the reply to the replies collection of this event post and return true*/
        if(this.AttendeeCount<this.Capacity && (!getReplies().contains(reply))){
            ArrayList<Reply> toAdd=getReplies();
            toAdd.add(reply);

            setReplies(toAdd);
            setAttendeeCount(toAdd.size());
            if(getAttendeeCount()==getCapacity())
                setStatus("CLOSED");
            return true;
        }

        return false;
    }

    @Override
    public String getReplyDetails() {
        String ids=getPostDetails()+"Attendee list:   ";
        if(getAttendeeCount()==0)
            ids+="Empty";
        else {
            ArrayList<Reply> temp=getReplies();
            for(int i=0;i<temp.size();i++){
                if(i==temp.size()-1) {
                    ids += temp.get(i).getReponderid();
                }
                else
                    ids += temp.get(i).getReponderid() + ",";
            }
        }
        return ids;
    }
}
