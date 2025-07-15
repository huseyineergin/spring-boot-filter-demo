"use client";

import { Button, Link, Navbar, NavbarBrand, NavbarContent, NavbarItem } from "@heroui/react";
import clsx from "clsx";
import NextLink from "next/link";
import { usePathname } from "next/navigation";

const navLinks = [
  { name: "Games", href: "/dashboard/games" },
  { name: "Movies", href: "/dashboard/movies" },
  { name: "Musics", href: "/dashboard/musics" },
  { name: "TV Shows", href: "/dashboard/tv-shows" },
];

export default function NavigationBar() {
  const pathname = usePathname();

  return (
    <Navbar isBordered maxWidth="xl">
      <NavbarBrand>
        <p>CMS</p>
      </NavbarBrand>

      <NavbarContent className="hidden gap-4 sm:flex" justify="center">
        {navLinks.map((link) => {
          return (
            <NavbarItem key={link.name} isActive={pathname === link.href}>
              <Link
                as={NextLink}
                href={link.href}
                className={clsx("", {
                  "text-white": pathname !== link.href,
                })}
              >
                {link.name}
              </Link>
            </NavbarItem>
          );
        })}
      </NavbarContent>

      <NavbarContent justify="end">
        <NavbarItem>
          <Button color="primary" href="#" variant="flat">
            Sign Out
          </Button>
        </NavbarItem>
      </NavbarContent>
    </Navbar>
  );
}
