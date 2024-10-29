import { Button } from "@/components/ui/button";
import { Card, CardContent, CardHeader, CardTitle } from "@/components/ui/card";
import { Recipe } from "@/lib/types";
import Image from "next/image";
import Link from "next/link";

export default async function ListRecipes({ recipes }: { recipes: Recipe[] }) {
  return (
    <div className="container mx-auto p-4">
      <h1 className="text-3xl font-bold mb-4">My Recipes</h1>
      <Link href="/new">
        <Button className="mb-4">Create New Recipe</Button>
      </Link>
      <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-4">
        {recipes.map((recipe) => (
          <Card key={recipe.recipeId}>
            <CardHeader>
              <CardTitle>{recipe.title}</CardTitle>
            </CardHeader>
            <CardContent>
              {recipe.img && (
                <Image
                  src={recipe.img}
                  alt={recipe.title}
                  width={330}
                  height={280}
                  className="w-full h-[280px] object-cover mb-4"
                />
              )}
              <Link href={`/recipe/${recipe.recipeId}`}>
                <Button variant="outline">View Recipe</Button>
              </Link>
            </CardContent>
          </Card>
        ))}
      </div>
    </div>
  );
}
