import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;

import java.net.SocketTimeoutException;
import java.util.List;

public class ChatPost {
    private final Cohere cohere;

    public ChatPost(String apiKey) {
        this.cohere = Cohere.builder()
                .token(apiKey)
                .clientName("PlateMate")
                .build();
//        System.out.println("Cohere API client initialized with extended timeout.");

    }

    public String getResponse(String prompt) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a simple 1-week meal plan for someone who is allergic to nuts.")
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to nuts.").build()),
                                        Message.chatbot(ChatMessage.builder().message("Generate a simple 1-week meal plan for someone who is allergic to nuts.").build()))).build());

        System.out.println(response);

        return response.getText();
    }


}

//import com.cohere.api.Cohere;
//import com.cohere.api.resources.v2.requests.V2ChatRequest;
//import com.cohere.api.types.*;
//import java.util.List;
//import java.util.Optional;
//
//public class ChatPost {
//    private final Cohere cohere;
//
//    public ChatPost(String apiKey) {
//        this.cohere = Cohere.builder()
//                .token(apiKey)
//                .clientName("PlateMate")
//                .build();
//    }
//
//    public Optional<AssistantMessageResponse> getMealPlan(String dietaryRestrictions, String allergies, String healthGoals) {
//        String prompt = "Give me a 1-week meal plan which adheres to these restrictions: " +
//                "dietary restrictions: " + dietaryRestrictions + ", " +
//                "allergies: " + allergies + ", " +
//                "health goals: " + healthGoals + ". " +
//                "I want the meal plan to have daily breakfast, lunch, dinner, and a snack where each meal has a main dish, side, and a drink. I want you to return meals, recipes, ingredients, and nutritional value.";
//
//        ChatResponse response = cohere.v2().chat(
//                V2ChatRequest.builder()
//                        .model("command-r-plus")
//                        .messages(List.of(ChatMessageV2.user(
//                                UserMessage.builder()
//                                        .content(UserMessageContent.of(prompt))
//                                        .build()
//                        )))
//                        .build());
//
//        // Inspect and adjust this part based on the actual method in ChatResponse
//        return response.getMessage(); // Adjust based on actual method
//
//    }
//    public Optional<AssistantMessageResponse> getResponse(String prompt) {
//        // Build and send the request to Cohere
//        ChatResponse response = cohere.v2().chat(
//                V2ChatRequest.builder()
//                        .model("command-r-plus")
//                        .messages(List.of(ChatMessageV2.user(
//                                UserMessage.builder()
//                                        .content(UserMessageContent.of(prompt))
//                                        .build()
//                        )))
//                        .build()
//        );
//
//        // Extract the response content
//        return response.getMessage(); // Adjust this based on the correct Cohere method
//    }
//
//}
