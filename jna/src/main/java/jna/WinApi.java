package jna;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Pointer;
import com.sun.jna.ptr.IntByReference;
import com.sun.jna.win32.W32APIOptions;

public class WinApi
{
    public interface Kernel32 extends Library
    {
        int GetLastError ();

        boolean WriteFile (Pointer hFile, byte[] lpBuffer, int nNumberOfBytesToWrite,
                           IntByReference lpNumberOfBytesWritten,
                           Pointer lpOverlapped);
    }

    static final Kernel32 kernel32 = Native.load("kernel32", Kernel32.class, W32APIOptions.DEFAULT_OPTIONS);

    static final Pointer INVALID_HANDLE_VALUE = Pointer.createConstant(Native.POINTER_SIZE == 8 ? -1 : 0xFFFFFFFFL);

    public static void main (String[] args)
    {
        final var res = kernel32.WriteFile(INVALID_HANDLE_VALUE, null, 0, null, null);
        final var err = kernel32.GetLastError();
        System.out.printf("WriteFile result: %s, GetLastError result: %d\n", res, err);
    }
}
