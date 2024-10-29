"use client";

import { Button } from "@/components/ui/button";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { createRecipe } from "@/lib/api";
import { Ingredient } from "@/lib/types";
import Link from "next/link";
import { useRouter } from "next/navigation";
import { useState } from "react";

export default function NewRecipePage() {
  const [title, setTitle] = useState("");
  const [img, setImg] = useState("");
  const [ingredients, setIngredients] = useState<Ingredient[]>([]);
  const [error, setError] = useState<string | null>(null);
  const router = useRouter();

  const handleSubmit = async (e: React.FormEvent) => {
    e.preventDefault();
    try {
      await createRecipe({
        title,
        img,
        ingredients,
      });
      router.push("/");
    } catch (error) {
      console.log(error);
      setError("Failed to create recipe");
    }
  };

  const addIngredient = () => {
    setIngredients([
      ...ingredients,
      { ingredientId: Date.now(), name: "", quantity: 1, measure: "" },
    ]);
  };

  const removeIngredient = (index: number) => {
    const updatedIngredients = ingredients.filter((_, i) => i !== index);
    setIngredients(updatedIngredients);
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
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">Create New Recipe</h1>
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
        <div>
          <Label htmlFor="img">Image URL</Label>
          <Input
            id="img"
            value={img}
            onChange={(e) => setImg(e.target.value)}
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
                variant="destructive"
                onClick={() => removeIngredient(index)}
              >
                Remove
              </Button>
            </div>
          ))}
          <Button
            className="w-min"
            variant={"outline"}
            type="button"
            onClick={addIngredient}
          >
            Add Ingredient
          </Button>
        </div>
        {error && <p className="text-red-500">{error}</p>}
        <Button type="submit">Create Recipe</Button>
      </form>

      <div className="mt-8">
        <Button variant={"link"} asChild>
          <Link href={"/"}>Back to Home</Link>
        </Button>
      </div>
    </div>
  );
}
