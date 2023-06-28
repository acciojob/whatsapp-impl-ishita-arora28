package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }
    public String createUser(String name,String mobile) throws Exception {
        if(userMobile.contains(mobile)){
            throw new Exception("User already exists");
        }
        userMobile.add(mobile);
        User user=new User(name,mobile);
        return "SUCCESS"; 
    }
    public Group createGroup(List<User> users){
        if(users.size()==2){
            Group group=new Group(users.get(1).getName(),2);
            adminMap.put(group,users.get(0));
            groupUserMap.put(group,users);
            groupMessageMap.put(group,new ArrayList<Message>());
        }
        this.customGroupCount+=1;
        Group group=new Group(new String("Group "+this.customGroupCount), users.size());
        adminMap.put(group,users.get(0));
         groupUserMap.put(group,users);
            groupMessageMap.put(group,new ArrayList<Message>());
            return group;
    }
    public int createMessage(String content) {
        this.messageId+=1;
        Message message=new Message(this.messageId,content);
        return message.getId();

    }
    public int sendMessage(Message message, User sender, Group group) throws Exception{
    
        if(adminMap.containsKey(group)){
            List<User> users=groupUserMap.get(group);
            Boolean userFound=false;
            for(User user:users){
                if(user.equals(sender)){
                    userFound=true;
                    break;
                }
            }
            if(userFound){
                senderMap.put(message,sender);
                List<Message> messages=groupMessageMap.get(group);
                messages.add(message);
                groupMessageMap.put(group,messages);
                return messages.size();
            }
            throw new Exception("You are not allowed to send message");
        }
        throw new Exception("Group does not exist");
    }
    public String changeAdmin(User approver, User user, Group group) throws Exception{
         if(adminMap.containsKey(group)){
            User adminUserGroup=adminMap.get(group);
            if(adminUserGroup.equals(approver)){
                List<User> participants=groupUserMap.get(group);
                Boolean userFound=false;
                for(User p:participants){
                    if(p.equals(user)){
                        userFound=true;
                        break;
                    }
                }
                if(userFound){
                    adminMap.put(group,user);
                    return "SUCCESS";
                }
                throw new Exception("User is not a participant");

            }
            throw new Exception("Approver does not have rights");
         }
         throw new Exception("Group does not exist");

    }
    public int removeUser(User user) throws Exception{
         //This is a bonus problem and does not contains any marks
        //A user belongs to exactly one group
        //If user is not found in any group, throw "User not found" exception
        //If user is found in a group and it is the admin, throw "Cannot remove admin" exception
        //If user is not the admin, remove the user from the group, remove all its messages from all the databases, and update relevant attributes accordingly.
        //If user is removed successfully, return (the updated number of users in the group + the updated number of messages in group + the updated number of overall messages)
        // for(Group group: groupUserMap.keySet()){
        //     List<User> members=groupUserMap.get(group);
        //     for(User member: members){
        //         if(member.equals(user)){
        //             Boolean userFound=false;

        //             if(adminMap.get(group).equals(user)){
        //                 throw new Exception("Cannot remove admin");

        //             }
        //             members.remove(member);
        //             groupUserMap.put(group,members);
        //             List<Message> msgs=groupMessageMap.get(group);
                    

        //         }
        //         throws new Exception("User not found");
        //     }

        // }
        return 0;
    }
    public String findMessage(Date start, Date end, int k) {
        return null;
    }

}
