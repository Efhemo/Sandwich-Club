package com.udacity.sandwichclub.utils;

import android.text.TextUtils;
import android.util.Log;

import com.udacity.sandwichclub.model.Sandwich;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class JsonUtils {

    public static final String LOG_TAG = JsonUtils.class.getSimpleName();

    private JsonUtils() {
    }

    public static Sandwich parseSandwichJson(String sandwichData) {

        if (TextUtils.isEmpty(sandwichData)) {
            return null;
        }

        Sandwich sandwichObject = null;

        try {
            JSONObject sandwichDataInfo = new JSONObject(sandwichData);

            JSONObject name = sandwichDataInfo.getJSONObject("name");
            String mainName = name.getString("mainName");

            List<String> alsoKnownAsList = new ArrayList<>();
            JSONArray alsoKnownAsArray = name.getJSONArray("alsoKnownAs");
            int countAlsoKnownAsArray = alsoKnownAsArray.length();
            for (int i = 0; i < countAlsoKnownAsArray; i++) {
                String otherName = alsoKnownAsArray.getString(i);
                alsoKnownAsList.add(otherName);
            }

            String placeOfOrigin = sandwichDataInfo.getString("placeOfOrigin");

            String description = sandwichDataInfo.getString("description");
            String image = sandwichDataInfo.getString("image");

            List<String> ingredientsList = new ArrayList<>();
            JSONArray ingredientsArray = sandwichDataInfo.getJSONArray("ingredients");
            int countIngredientsArray = ingredientsArray.length();
            for (int j = 0; j < countIngredientsArray; j++) {
                String ingredient = ingredientsArray.getString(j);
                ingredientsList.add(ingredient);
            }

            sandwichObject = new Sandwich(mainName, alsoKnownAsList, placeOfOrigin, description, image, ingredientsList);


        } catch (JSONException e) {
            Log.e("JsonUtils", "Error Parsing Json Object", e);
        }

        return sandwichObject;
    }
}
