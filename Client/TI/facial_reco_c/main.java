public class main 
{
    public static void main(String argv[]) 
    {
        System.loadLibrary("Hough"); // Attempts to load example.dll (on Windows) or libexample.so (on Linux)
        System.out.println(Hough.foo());
    }
}
