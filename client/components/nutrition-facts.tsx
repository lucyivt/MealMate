import { Nutrient } from "@/lib/types";

interface NutritionFactsProps {
  nutrients: Nutrient[];
}

export function NutritionFacts({ nutrients }: NutritionFactsProps) {
  const findNutrient = (label: string) =>
    nutrients.find((n) => n.label === label);
  const formatValue = (value: number) => Math.round(value * 10) / 10;

  const calories = findNutrient("Energy");
  const totalFat = findNutrient("Total lipid (fat)");
  const saturatedFat = findNutrient("Fatty acids, total saturated");
  const transFat = findNutrient("Fatty acids, total trans");
  const cholesterol = findNutrient("Cholesterol");
  const sodium = findNutrient("Sodium, Na");
  const totalCarbs = findNutrient("Carbohydrate, by difference");
  const dietaryFiber = findNutrient("Fiber, total dietary");
  const totalSugars = findNutrient("Sugars, total including NLEA");
  const addedSugars = findNutrient("Sugars, added");
  const protein = findNutrient("Protein");
  const vitaminD = findNutrient("Vitamin D (D2 + D3)");
  const calcium = findNutrient("Calcium, Ca");
  const iron = findNutrient("Iron, Fe");
  const potassium = findNutrient("Potassium, K");

  return (
    <div className="bg-white p-4 rounded-lg shadow-md w-full max-w-sm">
      <h2 className="text-3xl font-bold mb-4">Nutrition Facts</h2>
      <div className="border-b-2 border-black pb-2 mb-2">
        <p className="font-bold">Amount Per Serving</p>
        <p className="text-4xl font-bold">
          Calories {formatValue(calories?.quantity || 0)}
        </p>
      </div>
      <table className="w-full">
        <tbody>
          <tr className="border-b border-gray-300">
            <td colSpan={3} className="text-right font-bold py-1">
              % Daily Value*
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              <strong>Total Fat</strong> {formatValue(totalFat?.quantity || 0)}g
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((totalFat?.quantity || 0) / 65) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="pl-4 py-1">
              Saturated Fat {formatValue(saturatedFat?.quantity || 0)}g
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((saturatedFat?.quantity || 0) / 20) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="pl-4 py-1">
              Trans Fat {formatValue(transFat?.quantity || 0)}g
            </td>
            <td></td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              <strong>Cholesterol</strong>{" "}
              {formatValue(cholesterol?.quantity || 0)}mg
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((cholesterol?.quantity || 0) / 300) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              <strong>Sodium</strong> {formatValue(sodium?.quantity || 0)}mg
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((sodium?.quantity || 0) / 2300) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              <strong>Total Carbohydrate</strong>{" "}
              {formatValue(totalCarbs?.quantity || 0)}g
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((totalCarbs?.quantity || 0) / 300) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="pl-4 py-1">
              Dietary Fiber {formatValue(dietaryFiber?.quantity || 0)}g
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((dietaryFiber?.quantity || 0) / 25) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="pl-4 py-1">
              Total Sugars {formatValue(totalSugars?.quantity || 0)}g
            </td>
            <td></td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="pl-8 py-1">
              Includes {formatValue(addedSugars?.quantity || 0)}g Added Sugars
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((addedSugars?.quantity || 0) / 50) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              <strong>Protein</strong> {formatValue(protein?.quantity || 0)}g
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((protein?.quantity || 0) / 50) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              Vitamin D {formatValue(vitaminD?.quantity || 0)}µg
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((vitaminD?.quantity || 0) / 20) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              Calcium {formatValue(calcium?.quantity || 0)}mg
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((calcium?.quantity || 0) / 1300) * 100)}%
              </strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">Iron {formatValue(iron?.quantity || 0)}mg</td>
            <td className="text-right py-1">
              <strong>{Math.round(((iron?.quantity || 0) / 18) * 100)}%</strong>
            </td>
          </tr>
          <tr className="border-b border-gray-300">
            <td className="py-1">
              Potassium {formatValue(potassium?.quantity || 0)}mg
            </td>
            <td className="text-right py-1">
              <strong>
                {Math.round(((potassium?.quantity || 0) / 4700) * 100)}%
              </strong>
            </td>
          </tr>
        </tbody>
      </table>
      <p className="text-xs mt-2">
        *Percent Daily Values are based on a 2000 calorie diet
      </p>
    </div>
  );
}
