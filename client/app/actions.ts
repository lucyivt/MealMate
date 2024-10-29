"use server";

import { API_ENDPOINTS } from "@/lib/baseUrl";
import { ActionsErrorResult, AuthInfo, AuthResponse } from "@/lib/types";
import { cookies } from "next/headers";
import { redirect } from "next/navigation";

export async function handleRegister(
  previousState: ActionsErrorResult,
  formData: FormData
) {
  const cookieStore = await cookies();
  const rawData = {
    firstName: formData.get("firstName") as string,
    lastName: formData.get("lastName") as string,
    password: formData.get("password") as string,
  } satisfies AuthInfo;
  console.log(rawData);

  try {
    const res = await fetch(API_ENDPOINTS.register, {
      method: "POST",
      body: JSON.stringify(rawData),
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) {
      console.log(await res.text());
      return { message: "Failed to register" } satisfies ActionsErrorResult;
    }

    const data = (await res.json()) as AuthResponse;
    cookieStore.set({
      name: "userId",
      value: `${data.userId}`,
      path: "/",
      httpOnly: false,
      secure: false,
      sameSite: "lax",
    });
  } catch (error) {
    console.log(error);
    return { message: "Failed to register" } satisfies ActionsErrorResult;
  }

  return redirect("/");
}

export async function handleLogin(
  previousState: ActionsErrorResult,
  formData: FormData
) {
  const cookieStore = await cookies();
  const rawData = {
    firstName: formData.get("firstName") as string,
    lastName: formData.get("lastName") as string,
    password: formData.get("password") as string,
  } satisfies AuthInfo;
  console.log(rawData);

  try {
    const res = await fetch(API_ENDPOINTS.login, {
      method: "POST",
      body: JSON.stringify(rawData),
      headers: {
        "Content-Type": "application/json",
      },
    });
    if (!res.ok) {
      console.log(await res.text());
      return { message: "Failed to register" } satisfies ActionsErrorResult;
    }

    const data = (await res.json()) as AuthResponse;
    cookieStore.set({
      name: "userId",
      value: `${data.userId}`,
      path: "/",
      httpOnly: false,
      secure: false,
      sameSite: "lax",
    });
  } catch (error) {
    console.log(error);
    return { message: "Failed to login" } satisfies ActionsErrorResult;
  }

  return redirect("/");
}
