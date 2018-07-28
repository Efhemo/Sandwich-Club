package com.udacity.sandwichclub;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Picasso;
import com.udacity.sandwichclub.model.Sandwich;
import com.udacity.sandwichclub.utils.JsonUtils;

import org.w3c.dom.Text;

import java.util.List;

public class DetailActivity extends AppCompatActivity {

    public static final String EXTRA_POSITION = "extra_position";
    private static final int DEFAULT_POSITION = -1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        ImageView ingredientsIv = findViewById(R.id.image_iv);

        Intent intent = getIntent();
        if (intent == null) {
            closeOnError();
        }

        int position = intent.getIntExtra(EXTRA_POSITION, DEFAULT_POSITION);
        if (position == DEFAULT_POSITION) {
            // EXTRA_POSITION not found in intent
            closeOnError();
            return;
        }

        String[] sandwiches = getResources().getStringArray(R.array.sandwich_details);
        String json = sandwiches[position];
        Sandwich sandwich = JsonUtils.parseSandwichJson(json);
        if (sandwich == null) {
            // Sandwich data unavailable
            closeOnError();
            return;
        }

        populateUI(sandwich);
        Picasso.with(this)
                .load(sandwich.getImage())
                .into(ingredientsIv);

        setTitle(sandwich.getMainName());

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    private void closeOnError() {
        finish();
        Toast.makeText(this, R.string.detail_error_message, Toast.LENGTH_SHORT).show();
    }

    private void populateUI(Sandwich sandwich) {
        TextView originTextView = findViewById(R.id.origin_tv);
        TextView alsoKnownAsLabel = findViewById(R.id.also_known_as_label);
        TextView alsoKnownAsTextView = findViewById(R.id.also_known_tv);
        TextView descriptionTextView = findViewById(R.id.description_tv);
        TextView ingredientsTextView = findViewById(R.id.ingredients_tv);


        String originText = sandwich.getPlaceOfOrigin();
        if (originText.isEmpty()){
            originTextView.setText(R.string.message_unknown);
        } else {
            originTextView.setText(originText);
        }

        List<String> alsoKnownAsList = sandwich.getAlsoKnownAs();

        if (alsoKnownAsList.size() == 0){
            alsoKnownAsLabel.setVisibility(View.GONE);
            alsoKnownAsTextView.setVisibility(View.GONE);
        } else {
            alsoKnownAsLabel.setVisibility(View.VISIBLE);
            alsoKnownAsTextView.setVisibility(View.VISIBLE);


            StringBuilder otherNames = new StringBuilder();


            for (String otherName : alsoKnownAsList) {
                otherNames.append(otherName).append(", ");
            }
            // Remove comma and space from the last name
            otherNames.setLength(otherNames.length() - 2);

            // Set & display the other names in the TextView
            alsoKnownAsTextView.setText(otherNames);
        }

        // Get the ingredients for the Sandwich
        List<String> ingredientsList = sandwich.getIngredients();
        // if the List is empty, set the Text as "Unknown"
        if (ingredientsList.size() == 0){
            ingredientsTextView.setText(R.string.message_unknown);
        } else { // otherwise set the ingredients using a StringBuilder
            StringBuilder ingredients = new StringBuilder();


            for (String ingredient : ingredientsList) {
                ingredients.append(ingredient).append(", ");
            }

            ingredients.setLength(ingredients.length() - 2);

            ingredientsTextView.setText(ingredients);
        }


        String description = sandwich.getDescription();
        // if the Description is empty, set the Text as "Not Available"
        if (description.isEmpty()) {
            descriptionTextView.setText(R.string.message_not_available);
        } else { // otherwise set & display the description
            descriptionTextView.setText(description);
        }
    }
}