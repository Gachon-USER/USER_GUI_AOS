package com.example.user;

public class recipeCooking {

    int idx;
    String cooking_order;
    int cooking_order_no;
    recipe_info recipeInfo;

    public recipeCooking(int idx, String cooking_order, int cooking_order_no, recipe_info recipeInfo) {
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
        this.recipeInfo = recipeInfo;
    }
    public recipeCooking(int idx, String cooking_order, int cooking_order_no) {
        this.idx = idx;
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
    }
    public recipeCooking(String cooking_order, int cooking_order_no) {
        this.cooking_order = cooking_order;
        this.cooking_order_no = cooking_order_no;
    }

    public int getIdx() {
        return idx;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public String getCooking_order() {
        return cooking_order;
    }

    public void setCooking_order(String cooking_order) {
        this.cooking_order = cooking_order;
    }

    public int getCooking_order_no() {
        return cooking_order_no;
    }

    public void setCooking_order_no(int cooking_order_no) {
        this.cooking_order_no = cooking_order_no;
    }

    public recipe_info getRecipe_info() {
        return recipeInfo;
    }

    public void setRecipeInfo(recipe_info recipeInfo) {
        this.recipeInfo = recipeInfo;
    }
}
