package jnr;

import jnr.ffi.LibraryLoader;
import jnr.ffi.Pointer;
import jnr.ffi.byref.IntByReference;

public class WinApi
{
    public interface Kernel32
    {
        int GetLastError ();

        boolean WriteFile (Pointer hFile, byte[] lpBuffer, int nNumberOfBytesToWrite,
                           IntByReference lpNumberOfBytesWritten,
                           Pointer lpOverlapped);
    }

    static final Kernel32 kernel32 = LibraryLoader.create(Kernel32.class).load("kernel32");

    static final jnr.ffi.Runtime runtime = jnr.ffi.Runtime.getRuntime(kernel32);

    static final Pointer INVALID_HANDLE_VALUE = Pointer.newIntPointer(runtime, runtime.addressSize() == 8 ? -1 : 0xFFFFFFFFL);

    public static void main (String[] args)
    {
        final var res = kernel32.WriteFile(INVALID_HANDLE_VALUE, null, 0, null, null);
        final var err = kernel32.GetLastError();
        System.out.printf("WriteFile result: %s, GetLastError result: %d\n", res, err);
    }
}
