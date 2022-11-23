package com.example.user;

public class recipe_data {

    String[] ingredient;
    String[] ingredient_cp;
    String[] recipe;
    int maxPage;
    int maxItem;

    recipe_info info;

    public recipe_data(String[] ingredient, String[] ingredient_cp, String[] recipe, int maxPage, int maxItem,recipe_info info){
        this.ingredient = ingredient;
        this.ingredient_cp = ingredient_cp;
        this.recipe = recipe;
        this.maxPage = maxPage;
        this.maxItem = maxItem;
        this.info = info;
    }

    public String getRecipeData (int page){
        String result = new String();

        result = recipe[page];

        return result;
    }

    public String getIngredient (int page){
        String result = new String();

        result = ingredient[page];

        return result;
    }

    public String getIngredient_cp (int page){
        String result = new String();

        result = ingredient_cp[page];

        return result;
    }

    public int getMaxPage(){
        return maxPage;
    }

    public int getMaxItem(){
        return maxItem;
    }

    public void setRecipe(String[] recipe) {
        this.recipe = recipe;
    }

    public void setIngredient(String[] ingredient) {
        this.ingredient = ingredient;
    }

    public void setMaxItem(int maxItem) {
        this.maxItem = maxItem;
    }

    public void setMaxPage(int maxPage) {
        this.maxPage = maxPage;
    }

    public void setInfo(recipe_info info) {
        this.info = info;
    }

    public int getID() {
        return info.getID();
    }

    public String getName() {
        return info.getName();
    }

    public String getUrl() {
        return info.getUrl();
    }

    public void setIngredient_cp(String[] ingredient_cp) {
        this.ingredient_cp = ingredient_cp;
    }
}
