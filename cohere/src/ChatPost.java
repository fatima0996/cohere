import com.cohere.api.Cohere;
import com.cohere.api.requests.ChatRequest;
import com.cohere.api.resources.v2.requests.V2ChatRequest;
import com.cohere.api.types.*;

import java.net.SocketTimeoutException;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
                        .message(prompt)
                        .chatHistory(
                                List.of(Message.user(ChatMessage.builder().message(prompt).build()),
                                        Message.chatbot(ChatMessage.builder().message(prompt).build()))
                        ).build());
        return response.getText();
    }

    public String getResponseRecipes(String MealResponse) {
        NonStreamedChatResponse response = cohere.chat(
                ChatRequest.builder()
                        .message("Generate a recipe " + MealResponse)
                        .chatHistory(
                                List.of(
                                        Message.user(ChatMessage.builder().message("Generate a recipe" + MealResponse).build()),
                                        Message.chatbot(ChatMessage.builder().message("Here is a recipe " + MealResponse).build())
                                )
                        ).build());
        return response.getText();
    }


   public Map<String, String> parseSingleDayMealPlan(String mealPlan) {
            Map<String, String> singleDayMealPlan = new HashMap<>();

            // Regex to match "MealType: Description" (e.g., "- Breakfast: Scrambled Eggs")
            Pattern mealPattern = Pattern.compile("- (Breakfast|Lunch|Dinner): (.+)");
            Matcher mealMatcher = mealPattern.matcher(mealPlan);

            while (mealMatcher.find()) {
                String mealType = mealMatcher.group(1);  // Group 1: Meal type (e.g., Breakfast)
                String mealDescription = mealMatcher.group(2).trim();  // Group 2: Description
                singleDayMealPlan.put(mealType, mealDescription);
            }

            return singleDayMealPlan;
        }


        // Method to clean meal descriptions by removing filler words
    public static String cleanMealDescription(String description) {
        String[] fillerWords = {"with", "that", "this", "or", "an", "by", "served", "and", "fill","in","them","together","favourite","your","roll","up" ,"are","a", "of", "on", "top", "topped", "made", "using"};
        List<String> keyIngredients = new ArrayList<>();

        // Split the description into individual words and filter out filler words
        String[] words = description.split(" ");
        for (String word : words) {
            if (!Arrays.asList(fillerWords).contains(word.toLowerCase())) {
                keyIngredients.add(word);
            }
        }

        return String.join(" ", keyIngredients);
    }

    public static Map<String, Map<String, String>> formatMealDetails(Map<String, String> rawMealDetails) {
        Map<String, Map<String, String>> formattedMealDetails = new LinkedHashMap<>();

        for (Map.Entry<String, String> entry : rawMealDetails.entrySet()) {
            String mealName = entry.getKey(); // e.g., Breakfast, Lunch, Dinner
            String mealContent = entry.getValue();

            Map<String, String> sections = new LinkedHashMap<>();

            // Extract Recipe, Instructions, Grocery List, Ingredients
            String[] headers = {"Recipe", "Instructions", "Grocery List", "Ingredients"};
            for (String header : headers) {
                Pattern headerPattern = Pattern.compile("- \\*\\*" + header + ":\\*\\* (.*?)(?=\\n- |$)", Pattern.DOTALL);
                Matcher headerMatcher = headerPattern.matcher(mealContent);

                if (headerMatcher.find()) {
                    String content = headerMatcher.group(1).trim();

                    // For Instructions: split sentences into separate lines
                    if (header.equals("Instructions")) {
                        String[] sentences = content.split("\\.\\s+");
                        StringBuilder formattedInstructions = new StringBuilder();
                        for (String sentence : sentences) {
                            formattedInstructions.append("- ").append(sentence.trim()).append(".\n");
                        }
                        sections.put(header, formattedInstructions.toString().trim());
                    } else {
                        sections.put(header, content);
                    }
                }
            }

            formattedMealDetails.put(mealName, sections);
        }

        return formattedMealDetails;
    }

    public static void printFormattedMealPlan(Map<String, Map<String, String>> formattedMealDetails) {
        for (Map.Entry<String, Map<String, String>> mealEntry : formattedMealDetails.entrySet()) {
            // Print main meal header in uppercase and a larger "visual size"
            System.out.println("\n=======================");
            System.out.println("   " + mealEntry.getKey().toUpperCase());
            System.out.println("=======================\n");

            for (Map.Entry<String, String> sectionEntry : mealEntry.getValue().entrySet()) {
                // Print subheaders (Recipe, Instructions, etc.) in smaller "header style"
                System.out.println("  --- " + sectionEntry.getKey() + " ---");
                System.out.println("    " + sectionEntry.getValue());
                System.out.println(); // Add spacing between sections
            }
        }
    }

    // Main method to test the parsing and cleaning
    public static void main(String[] args) {
        // Sample input meal plan as a single string
        String apiKey = "r4A0YoQcxKECMc4f2ipQT7PcKDqljAY8nYoLaETX";
        String mealPlan = "**Breakfast:**\n" +
                "- Halal Scrambled Eggs: Whisk together some eggs, milk (dairy or plant-based), salt, and pepper. Scramble them in a pan with a small amount of halal-certified cooking oil. Serve with toasted pita bread and a side of sliced fresh fruit like apples or pears.\n" +
                "- Oatmeal with Dates and Cinnamon: Cook oatmeal in milk or water, and top it with chopped dates, a pinch of cinnamon, and a drizzle of honey. Dates are a great natural sweetener and provide energy for the morning.\n" +
                "\n" +
                "**Lunch:**\n" +
                "- Grilled Chicken and Vegetable Skewers: Marinate halal chicken cubes in a mixture of olive oil, garlic, lemon juice, and your favorite halal-certified spices (e.g., paprika, cumin). Alternate the chicken with bell peppers, onions, and cherry tomatoes on skewers and grill or bake until cooked through. Serve with quinoa or rice and a simple green salad.\n" +
                "- Hummus and Vegetable Wrap: Spread some halal hummus on a whole wheat wrap, fill it with sliced carrots, cucumbers, lettuce, and halal-certified falafel (optional). Roll it up and enjoy with a side of roasted potato wedges.\n" +
                "\n" +
                "**Dinner:**\n" +
                "- Baked Salmon with Lemon and Dill: Place a salmon fillet on a bed of sliced lemons and dill. Drizzle with olive oil, season with salt and pepper, and bake until flaky. Serve with steamed broccoli and quinoa.\n" +
                "- Lamb and Vegetable Curry: Cook a halal lamb curry using cubed lamb shoulder, onions, garlic, ginger, tomatoes, and a blend of halal-certified spices like turmeric, coriander, and cumin. Add potatoes, carrots, and peas. Serve with steamed rice or naan bread.";

        // Create an instance of ChatPost
        ChatPost chatPost = new ChatPost(apiKey);

        // Parse the meal plan string into a map
        Map<String, String> parsedMealPlan = chatPost.parseSingleDayMealPlan(mealPlan);

        // Print the parsed meal plan
        System.out.println("\nParsed Meal Plan:");
        System.out.println(parsedMealPlan);
        for (Map.Entry<String, String> entry : parsedMealPlan.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }
    }

/*
    public Map<String, String> parseSingleDayMealPlan(String mealPlan) {
        Map<String, String> singleDayMealPlan = new HashMap<>();

        Pattern dayPattern = Pattern.compile("\\*\\*Day 1:\\*\\*(.*?)(?=(\\*\\*Day \\d+:\\*\\*|$))", Pattern.DOTALL);
        Matcher dayMatcher = dayPattern.matcher(mealPlan);

        if (dayMatcher.find()) {
            String dayContent = dayMatcher.group(1).trim();
            String[] mealLabels = {"Breakfast", "Lunch", "Dinner"};

            for (String label : mealLabels) {
                Pattern mealPattern = Pattern.compile("- " + label + ": (.*?)(?=(\\n- |$))", Pattern.DOTALL);
                Matcher mealMatcher = mealPattern.matcher(dayContent);

                if (mealMatcher.find()) {
                    String mealDescription = mealMatcher.group(1).trim();
                    String cleanedMeal = cleanMealDescription(mealDescription);
                    singleDayMealPlan.put(label, cleanedMeal);
                }
            }
        }

        return singleDayMealPlan;
    }

    public static String cleanMealDescription(String description) {
        String[] fillerWords = {"with", "that", "this", "or","an","by", "served", "and", "a", "of", "on", "top", "topped", "made", "using"};
        List<String> keyIngredients = new ArrayList<>();

        // Split the description into individual words and filter out filler words
        String[] words = description.split(" ");
        for (String word : words) {
            if (!Arrays.asList(fillerWords).contains(word.toLowerCase())) {
                keyIngredients.add(word);
            }
        }

        return String.join(" ", keyIngredients);
    }

*/


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
