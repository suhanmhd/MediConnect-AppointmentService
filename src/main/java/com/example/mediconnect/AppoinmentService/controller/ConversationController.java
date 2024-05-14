// package com.example.mediconnect.AppoinmentService.controller;

// import com.example.mediconnect.AppoinmentService.dto.MessageDto;
// import com.example.mediconnect.AppoinmentService.entity.Conversation;
// import com.example.mediconnect.AppoinmentService.entity.ConversationMember;
// import com.example.mediconnect.AppoinmentService.entity.Message;
// import com.example.mediconnect.AppoinmentService.repository.ConversationMemberRepository;
// import com.example.mediconnect.AppoinmentService.repository.ConversationRepository;
// import com.example.mediconnect.AppoinmentService.repository.MessageRepository;
// import com.example.mediconnect.AppoinmentService.service.MessageService;
// import org.springframework.beans.factory.annotation.Autowired;
// import org.springframework.data.domain.Sort;
// import org.springframework.http.HttpStatus;
// import org.springframework.http.ResponseEntity;
// import org.springframework.web.bind.annotation.*;

// import java.util.*;

// @RestController
// @RequestMapping("/appointment")
// public class ConversationController {


//     @Autowired
//     private MessageService messageService;

//     @Autowired
//     private ConversationRepository conversationRepository;

//     @Autowired
//     private MessageRepository messageRepository;
//     @Autowired
//     private ConversationMemberRepository conversationMemberRepository;


// //    @PostMapping("/newConvo/{firstUser}/{secondUser}")
// //    public ResponseEntity< Map<String, List<Message>>> newConversation(@PathVariable("firstUser") UUID senderId, @PathVariable("secondUser") UUID receiverId) {
// //        System.out.println(senderId + "        " + receiverId);
// //        ArrayList<String> members = new ArrayList<>();
// //        members.add(senderId.toString());
// //        members.add(receiverId.toString());
// //        System.out.println(members);
// //        Map<String, List<Message>> response = new HashMap<>();
// //        List<Conversation> conversations = conversationRepository.findAll();
// //
// //        Optional<Conversation> conv = conversations.stream()
// //                .filter(c -> c.getMembers().contains(senderId.toString()) && c.getMembers().contains(receiverId.toString()))
// //                .findFirst();
// //
// //        if(conv.isPresent()) {
// //
// //            UUID conversationId = conv.get().getConversationId();
// //
// //            Conversation conversation = conversationRepository.getById(conversationId);
// //
// //            System.out.println(conversation);
// ////
// //
// //            List<Message> message = messageRepository.findByConversation(conversation, Sort.by(Sort.Direction.ASC, "createdAt"));
// //            response.put("conversation", message);
// //
// //            return ResponseEntity.ok(response);
// //        }
// //        else{
// //
// //
// //            // Create a new conversation
// //            Conversation conversation = new Conversation();
// //            conversation.setMembers(Arrays.asList(senderId.toString(), receiverId.toString()));
// //
// //
// //            Conversation savedConversation = conversationRepository.save(conversation);
// //
// ////    response.put("conversation",savedConversation);
// //
// //            return ResponseEntity.ok(response);
// //        }



//     //

//     @PostMapping("/newConvo/{firstUser}/{secondUser}")
//     public ResponseEntity<Map<String, List<Message>>> newConversation(@PathVariable("firstUser") UUID senderId, @PathVariable("secondUser") UUID receiverId) {
//         System.out.println(senderId + "        " + receiverId);

//         // Get the member names based on senderId and receiverId
//         String senderName = conversationMemberRepository.findById(senderId).orElse(null).getMemberName();
//         String receiverName = conversationMemberRepository.findById(receiverId).orElse(null).getMemberName();

//         System.out.println(senderName + "        " + receiverName);

//         Map<String, List<Message>> response = new HashMap<>();

//         // Find conversations where both sender and receiver are members
//         List<Conversation> conversations = conversationRepository.findConversationsByMemberName(senderName);

//         for (Conversation conversation : conversations) {
//             if (conversation.getMembers().stream().anyMatch(member -> member.getMemberName().equals(receiverName))) {
//                 // Found existing conversation
//                 List<Message> messages = messageRepository.findByConversation(conversation, Sort.by(Sort.Direction.ASC, "createdAt"));
//                 response.put("conversation", messages);
//                 return ResponseEntity.ok(response);
//             }
//         }

//         // Create a new conversation
//         Conversation conversation = new Conversation();
//         List<ConversationMember> members = new ArrayList<>();
//         members.add(new ConversationMember(null, conversation, senderName));
//         members.add(new ConversationMember(null, conversation, receiverName));
//         conversation.setMembers(members);

//         Conversation savedConversation = conversationRepository.save(conversation);

//         // Optionally, you can add some initial messages to the new conversation if needed

//         return ResponseEntity.ok(response);
//     }


//     @GetMapping("/conversation/{conversationId}")
//     public ResponseEntity<?> getMessages(@PathVariable("conversationId") UUID conversationId) {
//         System.out.println(conversationId);

//         Conversation conversation=conversationRepository.getById(conversationId);

//         System.out.println(conversation);
// //
//         try {
//             List<Message>   message = messageRepository.findByConversation(conversation, Sort.by(Sort.Direction.ASC, "createdAt"));
//             System.out.println("<<<"+message);

//             return ResponseEntity.ok().body(message);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//         }
//     }




//     @PostMapping("/messages")
//     public ResponseEntity<?> newMessage(@RequestBody MessageDto messageReq) {
//         Conversation conversations=conversationRepository.getById(messageReq.getConversationId());
//         System.out.println(conversations);



// //        message.setText(messageRequest.getText());
//         Message  messages = new Message()
//                 .builder()
//                 .senderId(messageReq.getSenderId())
//                 .conversation(conversations)
//                 .text(messageReq.getText())
//                 .build();
// //        Conversation conversation =conversationRepository.getById(messageReq.getConversationId());

//         System.out.println(conversations);



//         System.out.println(messages);
//         System.out.println("Inside backend");

// //        try {
//         Message savedMessage = messageRepository.save(messages);
//         return ResponseEntity.ok().body(savedMessage);
// //        } catch (Exception e) {
// //            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
// //        }
//     }




//     @GetMapping("/getConvo/{userId}")
//     public ResponseEntity<Map<String, Object>> getConversation(@PathVariable("userId") UUID userId) {
//         System.out.println(userId);

//         try {
//             List<Conversation> conversations = messageService.getConversation(userId);
//             System.out.println("+++"+conversations);
//             Map<String, Object> response = new HashMap<>();
//             response.put("conversation", conversations);
//             response.put("status", "ok");
//             return ResponseEntity.ok(response);
//         } catch (Exception e) {
//             Map<String, Object> errorResponse = new HashMap<>();
//             errorResponse.put("error", e.getMessage());
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
//         }
//     }



// //    @GetMapping("/getMessages/{convoId}")
// //    public ResponseEntity<Map<String,Object>>getMessages(@PathVariable("convoId") UUID conversationId){
// //
// //        messageService.getMessages(conversationId);
// //        Map<String, Object> response = new HashMap<>();
// //        return ResponseEntity.ok(response);
// //
// //    }

//     @GetMapping("/getMessages/{convoId}")
//     public ResponseEntity<?> getChatMessage(@PathVariable("convoId") UUID conversationId) {
//         System.out.println(conversationId);


//         Conversation conversation=conversationRepository.findById(conversationId).orElse(null);

//         System.out.println(conversation);
// //
//         try {
// //            List<Message>   message = messageRepository.findByConversation(conversation);
// //            System.out.println("---"+message);

//             List<Message> message = messageRepository.findByConversation(
//                     conversation,
//                     Sort.by(Sort.Direction.DESC, "createdAt")
//             );
//             System.out.println("---"+message);
//             return ResponseEntity.ok().body(message);
//         } catch (Exception e) {
//             return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
//         }
//     }






//     @PostMapping("/newMessage")
//     public ResponseEntity<Map<String,Message>>sendMessages(@RequestBody MessageDto messageDto){

//        Message savedMessage= messageService.sendMessages(messageDto);

//         System.out.println("+"+messageDto);
//         Map<String, Message> response = new HashMap<>();
//         response.put("savedMessage",savedMessage);
//         return ResponseEntity.ok(response);

//     }



// }




package com.example.mediconnect.AppoinmentService.controller;

import com.example.mediconnect.AppoinmentService.dto.MessageDto;
import com.example.mediconnect.AppoinmentService.entity.Conversation;
import com.example.mediconnect.AppoinmentService.entity.Message;
import com.example.mediconnect.AppoinmentService.repository.ConversationRepository;
import com.example.mediconnect.AppoinmentService.repository.MessageRepository;
import com.example.mediconnect.AppoinmentService.service.MessageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping("/appointment")
public class ConversationController {

   @Autowired
   private MessageService messageService;

   @Autowired
   private ConversationRepository conversationRepository;

   @Autowired
   private MessageRepository messageRepository;

   @PostMapping("/newConvo/{firstUser}/{secondUser}")
   public ResponseEntity<Map<String, List<Message>>> newConversation(@PathVariable("firstUser") UUID senderId, @PathVariable("secondUser") UUID receiverId) {
       System.out.println(senderId + "        " + receiverId);
       ArrayList<String> members = new ArrayList<>();
       members.add(senderId.toString());
       members.add(receiverId.toString());
       System.out.println(members);
       Map<String, List<Message>> response = new HashMap<>();
       List<Conversation> conversations = conversationRepository.findAll();

       Optional<Conversation> conv = conversations.stream()
               .filter(c -> c.getMembers().contains(senderId.toString()) && c.getMembers().contains(receiverId.toString()))
               .findFirst();

       if (conv.isPresent()) {
           UUID conversationId = conv.get().getConversationId();
           Conversation conversation = conversationRepository.getById(conversationId);
           System.out.println(conversation);

           List<Message> messages = messageRepository.findByConversation(conversation, Sort.by(Sort.Direction.ASC, "createdAt"));
           response.put("conversation", messages);

           return ResponseEntity.ok(response);
       } else {
           Conversation conversation = new Conversation();
           conversation.setMembers(Arrays.asList(senderId.toString(), receiverId.toString()));
           Conversation savedConversation = conversationRepository.save(conversation);

           return ResponseEntity.ok(response);
       }
   }

   @GetMapping("/conversation/{conversationId}")
   public ResponseEntity<?> getMessages(@PathVariable("conversationId") UUID conversationId) {
       System.out.println(conversationId);

       Conversation conversation = conversationRepository.getById(conversationId);

       System.out.println(conversation);
//
       try {
           List<Message> message = messageRepository.findByConversation(conversation, Sort.by(Sort.Direction.ASC, "createdAt"));
           System.out.println("<<<" + message);

           return ResponseEntity.ok().body(message);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
   }

   @PostMapping("/messages")
   public ResponseEntity<?> newMessage(@RequestBody MessageDto messageReq) {
       Conversation conversation = conversationRepository.getById(messageReq.getConversationId());
       Message message = new Message()
               .builder()
               .senderId(messageReq.getSenderId())
               .conversation(conversation)
               .text(messageReq.getText())
               .build();

       Message savedMessage = messageRepository.save(message);
       return ResponseEntity.ok().body(savedMessage);
   }

   @GetMapping("/getConvo/{userId}")
   public ResponseEntity<Map<String, Object>> getConversation(@PathVariable("userId") UUID userId) {
       System.out.println(userId);

       try {
           List<Conversation> conversations = messageService.getConversation(userId);
           System.out.println("+++" + conversations);
           Map<String, Object> response = new HashMap<>();
           response.put("conversation", conversations);
           response.put("status", "ok");
           return ResponseEntity.ok(response);
       } catch (Exception e) {
           Map<String, Object> errorResponse = new HashMap<>();
           errorResponse.put("error", e.getMessage());
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponse);
       }
   }

   @GetMapping("/getMessages/{convoId}")
   public ResponseEntity<?> getChatMessage(@PathVariable("convoId") UUID conversationId) {
       System.out.println(conversationId);

       Conversation conversation = conversationRepository.findById(conversationId).orElse(null);

       System.out.println(conversation);

       try {
           List<Message> messages = messageRepository.findByConversation(
                   conversation,
                   Sort.by(Sort.Direction.DESC, "createdAt")
           );
           return ResponseEntity.ok().body(messages);
       } catch (Exception e) {
           return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
       }
   }

   @PostMapping("/newMessage")
   public ResponseEntity<Map<String, Message>> sendMessages(@RequestBody MessageDto messageDto) {

       Message savedMessage = messageService.sendMessages(messageDto);

       System.out.println("+" + messageDto);
       Map<String, Message> response = new HashMap<>();
       response.put("savedMessage", savedMessage);
       return ResponseEntity.ok(response);
   }
}
