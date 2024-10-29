import { NutritionFacts } from "@/components/nutrition-facts";
import { Button } from "@/components/ui/button";
import { getRecipe } from "@/lib/api";
import Image from "next/image";
import Link from "next/link";
import { EditRecipeForm } from "./edit-recipe-form";

// @ts-expect-error Failed to build if types params
export default async function RecipePage({ params }) {
  const { id } = (await params) as { id: string };
  const recipe = await getRecipe(+id);

  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">{recipe.title}</h1>
      <div className="flex gap-x-8">
        <div className="w-2/3">
          {recipe.img && (
            <Image
              src={recipe.img}
              alt={recipe.title}
              width={600}
              height={400}
              className="w-full col-span-2 max-w-2xl h-auto object-cover mb-4"
            />
          )}
          <EditRecipeForm recipe={recipe} />
        </div>
        <div className="w-1/3">
          <NutritionFacts nutrients={recipe.nutrients} />
        </div>
      </div>

      <div className="mt-8">
        <Button variant={"link"} asChild>
          <Link href={"/"}>Back to Home</Link>
        </Button>
      </div>
    </div>
  );
}
