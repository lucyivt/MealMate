export type AuthInfo = {
  firstName: string;
  lastName: string;
  password: string;
};

export type AuthResponse = {
  userId: number;
};

export type ActionsErrorResult = {
  message: string;
};

export type Recipe = {
  recipeId: number;
  title: string;
  img: string;
  userId: number;
  ingredients: Ingredient[];
  nutrients: Nutrient[];
};

export interface Ingredient {
  ingredientId: number;
  name: string;
  quantity: number;
  measure: string;
}

export interface Nutrient {
  label: string;
  quantity: number;
  unit: string;
}
