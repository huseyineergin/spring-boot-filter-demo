import Navbar from "./(components)/navbar";

export default function Layout({ children }: { children: React.ReactNode }) {
  return (
    <div className="flex h-screen flex-col">
      <Navbar />

      <div className="mx-auto w-full max-w-7xl px-6 py-4">{children}</div>
    </div>
  );
}
