"use server";

import { API_ENDPOINTS } from "@/lib/baseUrl";
import { Ingredient, Recipe } from "@/lib/types";
import { cookies } from "next/headers";

export async function getAllRecipes(): Promise<Recipe[]> {
  const cookieStore = await cookies();
  const userId = cookieStore.get("userId");
  if (!userId) throw new Error("User not logged in");

  const url = new URL(API_ENDPOINTS.recipes);
  url.searchParams.set("userId", userId.value);
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error("Failed to fetch recipes");
  }
  return response.json();
}

export async function getRecipe(id: number): Promise<Recipe> {
  const cookieStore = await cookies();
  const userId = cookieStore.get("userId");
  if (!userId) throw new Error("User not logged in");

  const url = new URL(`${API_ENDPOINTS.recipes}/${id}`);
  url.searchParams.set("userId", userId.value);
  const response = await fetch(url);
  if (!response.ok) {
    throw new Error("Failed to fetch recipe");
  }
  return response.json();
}

export async function createRecipe(
  recipe: Omit<Recipe, "recipeId" | "nutrients" | "userId" | "ingredients"> & {
    userId?: number;
    ingredients: Ingredient[];
  }
): Promise<void> {
  const cookieStore = await cookies();
  const userId = cookieStore.get("userId");
  if (!userId) throw new Error("User not logged in");

  recipe.userId = +userId.value;

  const ingredients = recipe.ingredients.map((ingredient) => {
    return {
      name: ingredient.name,
      quantity: ingredient.quantity,
      measure: ingredient.measure,
    } satisfies Omit<Ingredient, "ingredientId">;
  });
  // @ts-expect-error Want to remove the dummy id
  recipe.ingredients = ingredients;

  console.log("\n\nSENDING:\n");
  console.log(JSON.stringify(recipe, null, 2));

  const response = await fetch(API_ENDPOINTS.recipes, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(recipe),
  });
  if (!response.ok) {
    console.log(await response.text());
    throw new Error("Failed to create recipe");
  }
}

export async function updateRecipe(
  id: number,
  recipe: Omit<Recipe, "nutrients" | "userId" | "ingredients"> & {
    userId?: number;
    ingredients: Ingredient[];
  }
): Promise<void> {
  const cookieStore = await cookies();
  const userId = cookieStore.get("userId");
  if (!userId) throw new Error("User not logged in");

  recipe.userId = +userId.value;

  const ingredients = recipe.ingredients.map((ingredient) => {
    return {
      name: ingredient.name,
      quantity: ingredient.quantity,
      measure: ingredient.measure,
    } satisfies Omit<Ingredient, "ingredientId">;
  });
  // @ts-expect-error Want to remove the dummy id
  recipe.ingredients = ingredients;

  console.log("\n\nSENDING:\n");
  console.log(JSON.stringify(recipe, null, 2));
  const response = await fetch(`${API_ENDPOINTS.recipes}/${id}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify(recipe),
  });
  console.log(response.status);
  if (!response.ok) {
    console.log(await response.text());
    throw new Error("Failed to update recipe");
  }
}

export async function deleteRecipe(id: number): Promise<void> {
  const cookieStore = await cookies();
  const userId = cookieStore.get("userId");
  if (!userId) throw new Error("User not logged in");

  const url = new URL(`${API_ENDPOINTS.recipes}/${id}`);
  url.searchParams.set("userId", userId.value);

  const response = await fetch(url, {
    method: "DELETE",
  });
  if (!response.ok) {
    throw new Error("Failed to delete recipe");
  }
}
