"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { deleteRecipe, updateRecipe } from "@/lib/api";
import { Ingredient, Recipe } from "@/lib/types";
import { LucideTrash } from "lucide-react";
import { useRouter } from "next/navigation";
import { useState } from "react";

export function EditRecipeForm({ recipe }: { recipe: Recipe }) {
  const [title, setTitle] = useState(recipe.title);
  const [ingredients, setIngredients] = useState(recipe.ingredients);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await updateRecipe(recipe.recipeId, {
        title,
        img: recipe.img,
        recipeId: recipe.recipeId,
        ingredients,
      });
    } catch (error) {
      console.log(error);
      setError("Failed to update recipe");
    }
    router.refresh();
  };

  const handleDelete = async () => {
    try {
      await deleteRecipe(recipe.recipeId);
      router.push("/");
    } catch (error) {
      console.log(error);
      setError("Failed to delete recipe");
    }
  };

  const removeIngredient = (index: number) => {
    const updatedIngredients = ingredients.filter((_, i) => i !== index);
    setIngredients(updatedIngredients);
  };

  const addIngredient = () => {
    setIngredients([
      ...ingredients,
      { ingredientId: Date.now(), name: "", quantity: 0, measure: "" },
    ]);
  };

  const handleIngredientChange = (
    index: number,
    field: keyof Ingredient,
    value: string | number
  ) => {
    const updatedIngredients = [...ingredients];
    updatedIngredients[index] = {
      ...updatedIngredients[index],
      [field]: value,
    };
    setIngredients(updatedIngredients);
  };

  return (
    <form onSubmit={handleSubmit} className="space-y-4">
      <div>
        <Label htmlFor="title">Title</Label>
        <Input
          id="title"
          value={title}
          onChange={(e) => setTitle(e.target.value)}
          required
        />
      </div>
      <div className="flex flex-col gap-y-2">
        <Label>Ingredients</Label>
        {ingredients.map((ingredient, index) => (
          <div key={ingredient.ingredientId} className="flex space-x-2 mb-2">
            <Input
              value={ingredient.name}
              onChange={(e) =>
                handleIngredientChange(index, "name", e.target.value)
              }
              placeholder="Name"
            />
            <Input
              type="number"
              min={0}
              value={ingredient.quantity}
              onChange={(e) =>
                handleIngredientChange(
                  index,
                  "quantity",
                  parseFloat(e.target.value)
                )
              }
              placeholder="Quantity"
            />
            <Input
              value={ingredient.measure}
              onChange={(e) =>
                handleIngredientChange(index, "measure", e.target.value)
              }
              placeholder="Measure"
            />
            <Button
              type="button"
              size={"sm"}
              variant="destructive"
              className="rounded-full"
              onClick={() => removeIngredient(index)}
            >
              <LucideTrash />
            </Button>
          </div>
        ))}
        <Button
          type="button"
          variant={"outline"}
          onClick={addIngredient}
          className="w-min"
        >
          Add Ingredient
        </Button>
      </div>
      {error && <span className="text-red-500">{error}</span>}
      <div className="flex justify-between">
        <Button type="submit">Update Recipe</Button>
        <Button type="button" variant="destructive" onClick={handleDelete}>
          Delete Recipe
        </Button>
      </div>
    </form>
  );
}
