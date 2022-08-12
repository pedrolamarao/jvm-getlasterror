package panama;

import java.lang.foreign.*;
import java.lang.invoke.MethodHandle;

import static java.lang.foreign.MemoryAddress.NULL;
import static java.lang.foreign.ValueLayout.ADDRESS;
import static java.lang.foreign.ValueLayout.JAVA_INT;

public class WinApi {
    static final MethodHandle WriteFile$Func;
    static final MethodHandle GetLastError$Func;
    static final MemoryAddress INVALID_HANDLE_VALUE = MemoryAddress.ofLong(-1);

    static {
        var linker = Linker.nativeLinker();
        var lookup = SymbolLookup.libraryLookup("Kernel32", MemorySession.global());

        WriteFile$Func = linker.downcallHandle(
                lookup.lookup("WriteFile").get(),
                FunctionDescriptor.of(JAVA_INT, ADDRESS, ADDRESS, JAVA_INT, ADDRESS, ADDRESS)
        );
        GetLastError$Func = linker.downcallHandle(
                lookup.lookup("GetLastError").get(),
                FunctionDescriptor.of(JAVA_INT)
        );
    }

    public static void main(String[] args) {
        var res = WriteFile(INVALID_HANDLE_VALUE, NULL, 0, NULL, NULL);
        var err = GetLastError();
        System.out.printf("WriteFile result: %d, GetLastError result: %d\n", res, err);
    }

    static int WriteFile(MemoryAddress hFile, MemoryAddress lpBuffer, int nNumberOfBytesToWrite,
                         MemoryAddress lpNumberOfBytesWritten, MemoryAddress lpOverlapped) {
        try {
            return (int) WriteFile$Func.invokeExact((Addressable)hFile, (Addressable)lpBuffer, nNumberOfBytesToWrite,
                    (Addressable)lpNumberOfBytesWritten, (Addressable)lpOverlapped);
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    static int GetLastError() {
        try {
            return (int) GetLastError$Func.invokeExact();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}
