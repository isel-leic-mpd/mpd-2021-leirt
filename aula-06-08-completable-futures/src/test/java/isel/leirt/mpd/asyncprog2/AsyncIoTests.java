package isel.leirt.mpd.asyncprog2;


import isel.leirt.mpd.asyncprog2.asyncio.AsyncFile;
import org.junit.Test;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.IntStream;
import java.util.stream.Stream;


import static isel.leirt.mpd.asyncprog2.asyncio.FileUtils.copyFileAsync;
import static isel.leirt.mpd.streams3.StreamUtils.zip;
import static java.util.stream.Collectors.toList;
import static org.junit.Assert.assertEquals;


@SuppressWarnings("unchecked")
public class AsyncIoTests {

    // auxiliary file to synchronous tests
    private static void copyFile(String fin, String fout)  {
        try(InputStream is = new FileInputStream(fin);
            OutputStream os = new FileOutputStream(fout))  {
            byte[] buffer = new byte[4096];
            int size;
            while((size = is.read(buffer)) > 0)
                os.write(buffer, 0, size);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get sequence of file paths on folder with name "rootName"
     * @param rootName
     * @return
     */
    private static Stream<Path> getFilesFromFolder(String rootName) {
        Path root = Path.of(rootName);

        try {
            return Files.walk(root)
                    .filter(p -> Files.isRegularFile(p));
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    /**
     * Get  lines of file with pathname "p"
     * @param p
     * @return
     */
    private static Stream<String> getLines(Path p) {
        try {
            return Files.lines(p);
        }
        catch(IOException e) {
            throw new UncheckedIOException(e);
        }
    }

    // Synchronous tests


    @Test
    public void copy2FilesSequentialTest()  {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};

        copyFile("fin1.dat","fout1.dat" );
        copyFile("fin2.dat","fout2.dat" );
    }

    @Test
    public void countFilesLinesSequentialTest()  {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");

        long result = files
                .flatMap(AsyncIoTests::getLines)
                .count();
        System.out.println(result
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }


    // End of sequential tests


    @Test
    public void copy2FilesWithCompletableFutureTest() {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};
        long start = System.currentTimeMillis();

        CompletableFuture<Long> fl1 = copyFileAsync(fin[0], fout[0]);

        CompletableFuture<Long> fl2 = copyFileAsync(fin[1], fout[1]);
        CompletableFuture<Long> fres =
                fl1.thenCombine( fl2, (l1,l2) -> l1 +l2);

        long l = fres.join();
        System.out.println(l);
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void copy2FilesInSequenceWithCompletableFutureTest() {
        String[] fin = { "fin1.dat", "fin2.dat"};
        String[] fout = {"fout1.dat", "fout2.dat"};
        long start = System.currentTimeMillis();

        //
        // to complete!
        //
        CompletableFuture<Long> fres =
            copyFileAsync(fin[0], fout[0])
            .thenCompose(l ->
                        copyFileAsync(fin[1], fout[1])
                        .thenApply(l1 -> l + l1));


        long l = fres.join();
        System.out.println(l);
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }


    private static CompletableFuture<Long> copyAll(String[] fin, String[] fout) {
        // launch all copies!

        Stream<CompletableFuture<Long>>  streamCopies =
            IntStream.range(0, fin.length)
            .mapToObj(i -> copyFileAsync(fin[i], fout[i]));

        CompletableFuture<Long>[] allCopies =
            streamCopies
            .toArray(sz ->  new CompletableFuture[sz]);


         return   CompletableFuture.allOf(allCopies)
            .thenApply(__ -> {
                /*
                long sum = 0;
                for(CompletableFuture<Long> cf :  allCopies) {
                    sum += cf.join();
                }
                return sum;
                */

                return Arrays.stream(allCopies)
                .mapToLong(cf -> cf.join())
                .sum();
            });
    }

    @Test
    public void copyNFilesWithCompletableFutureAllOfTest() {
        String[] fin = { "fin1.dat", "fin2.dat", "fin3.dat", "fin4.dat" };
        String[] fout = { "fout.dat", "fout2.dat", "fout3.dat", "fout4.dat" };



        long start = System.currentTimeMillis();
        CompletableFuture<Long> total =
            copyAll(fin, fout);


        System.out.println(total.join());
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void copyNFilesWithCompletableFutureWithThenCombineTest() {
        String[] fin = { "fin1.dat", "fin2.dat", "fin3.dat", "fin4.dat" };
        String[] fout = { "fout.dat", "fout2.dat", "fout3.dat", "fout4.dat" };

        long start = System.currentTimeMillis();
        List<CompletableFuture<Long>> futureCopies =
                IntStream.range(0, fin.length)
                        .mapToObj(i -> copyFileAsync(fin[i], fout[i]))
                        .collect(toList());

        // A implementar
        CompletableFuture<Long> total =
            futureCopies
            .stream()
            .reduce(CompletableFuture.completedFuture(0L),
                (cf1, cf2) -> cf1.thenCombine(cf2, (l1, l2) -> l1 + l2));

        System.out.println(total.join());
        System.out.println( "done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }



    @Test
    public void countMultipleFilesLinesAsyncWithAllOfTest() {

        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");

        Stream<CompletableFuture<Long>> futCounts =
                files.map(p -> {
                        AsyncFile f = AsyncFile.open(p.toString());
                        return f.readLines()
                                .thenApply(s -> s.count())
                                .whenComplete((l, __) -> f.close());
                });

        CompletableFuture<Long>[] futArray =
                futCounts.toArray(s-> new CompletableFuture[s]);

        System.out.println("Start count!");

        CompletableFuture<Long> result = null;

        //
        // to implement


        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

    @Test
    public void countMultipleFilesLinesAsyncWithCombineTest() {
        long start = System.currentTimeMillis();
        Stream<Path> files = getFilesFromFolder("rdf-files.tar");


        CompletableFuture<Long>[] futCounts =
                files.map(p -> {
                    AsyncFile f = AsyncFile.open(p.toString());
                    return f.readLines()
                            .whenComplete((t, s) -> f.close())
                            .thenApply(s -> s.count());
                })
                .toArray(sz -> new CompletableFuture[sz]);

        System.out.println("Start count!");


        CompletableFuture<Long> result =
            CompletableFuture.allOf(futCounts)
            .thenApply(__ ->
                     Arrays.stream(futCounts)
                     .mapToLong(cf -> cf.join())
                     .sum()
            );


        //
        // to implement!
        //

        System.out.println(result.join()
                + ", done in "
                + (System.currentTimeMillis()-start) + " ms!");
    }

}
