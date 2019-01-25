package com.adnd.bakingapp.models;


import android.arch.persistence.room.Entity;
import android.arch.persistence.room.ForeignKey;
import android.arch.persistence.room.Index;
import android.arch.persistence.room.PrimaryKey;

@Entity(
        tableName = "widget_has_recipe",
        foreignKeys = @ForeignKey(
                entity = Recipe.class,
                parentColumns = "id",
                childColumns = "recipe_id"
        ),
        indices = {@Index("recipe_id")}
)
public class WidgetHasRecipe {

    @PrimaryKey
    private int widget_id;

    private int recipe_id;

    public int getWidget_id() {
        return widget_id;
    }

    public void setWidget_id(int widget_id) {
        this.widget_id = widget_id;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }
}
