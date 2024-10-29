import ListRecipes from "@/components/list-recipes";
import { getAllRecipes } from "@/lib/api";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export default async function Home() {
  const reqCookies = await cookies();
  const userId = reqCookies.get("userId");
  if (!userId) return redirect("/login");

  const recipes = await getAllRecipes();

  return (
    <div className="flex flex-col h-dvh w-full p-8 font-[family-name:var(--font-geist-sans)]">
      <main>
        <ListRecipes recipes={recipes} />
      </main>
    </div>
  );
}
