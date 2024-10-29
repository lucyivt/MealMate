"use client";

import Link from "next/link";

import { handleRegister } from "@/app/actions";
import { Button } from "@/components/ui/button";
import {
  Card,
  CardContent,
  CardDescription,
  CardHeader,
  CardTitle,
} from "@/components/ui/card";
import { Input } from "@/components/ui/input";
import { Label } from "@/components/ui/label";
import { ActionsErrorResult } from "@/lib/types";
import { useActionState } from "react";

const initialState = {
  message: "",
} satisfies ActionsErrorResult;

export function RegisterForm() {
  const [state, formAction] = useActionState(handleRegister, initialState);

  return (
    <Card className="mx-auto max-w-sm">
      <CardHeader>
        <CardTitle className="text-2xl">Register</CardTitle>
        <CardDescription>
          Enter your credentials below to register to your account
        </CardDescription>
      </CardHeader>
      <CardContent>
        <form action={formAction} className="grid gap-4">
          <div className="grid gap-2">
            <Label htmlFor="email">First Name</Label>
            <Input
              id="fistName"
              name="firstName"
              type="text"
              placeholder="John"
              required
            />
          </div>
          <div className="grid gap-2">
            <Label htmlFor="email">Last Name</Label>
            <Input
              id="lastName"
              name="lastName"
              type="text"
              placeholder="Doe"
              required
            />
          </div>
          <div className="grid gap-2">
            <div className="flex items-center">
              <Label htmlFor="password">Password</Label>
            </div>
            <Input id="password" name="password" type="password" required />
          </div>
          <p className="text-red-500">{state.message}</p>
          <Button type="submit" className="w-full">
            Register
          </Button>
        </form>
        <div className="mt-4 text-center text-sm">
          Already have an account?{" "}
          <Link href="/login" className="underline">
            Login
          </Link>
        </div>
      </CardContent>
    </Card>
  );
}
