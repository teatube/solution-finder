package _usecase.cover;

import _usecase.Log;
import _usecase.RunnerHelper;
import _usecase.cover.files.OutputFileHelper;
import entry.EntryPointMain;
import helper.CSVStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class CoverTetfuCaseTest {
    @Nested
    class FumenTest extends CoverUseCaseBaseTest {
        @Override
        @BeforeEach
        void setUp() throws IOException {
            super.setUp();
        }

        @Test
        void case1() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1680, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2240, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3108, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(812, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

            assertThat(csv.size()).isEqualTo(5040);
            assertThat(csv.row("name", "TIOLJSZ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "LISZTOJ"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "X");

            assertThat(csv.row("name", "TILSZJO"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "JSTILZO"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X");
        }

        @Test
        void case1WithoutHold() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            String command = String.format("cover -t %s %s -p *! --hold no", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(120, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(186, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(300, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(6, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

            assertThat(csv.size()).isEqualTo(5040);
            assertThat(csv.row("name", "JIZSOLT"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "SLIOZJT"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "X");

            assertThat(csv.row("name", "ILSZJOT"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "ZOTIJLS"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X");
        }

        @Test
        void case1Harddrop() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            String command = String.format("cover -t %s %s -p *! --drop harddrop", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1344, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1008, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1848, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(504, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

            assertThat(csv.size()).isEqualTo(5040);
            assertThat(csv.row("name", "JIOTLSZ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "OTLIZJS"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "X");

            assertThat(csv.row("name", "ILSJZOT"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "ZSLIOTJ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X");
        }

        @Test
        void case1WithoutHoldHarddrop() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            String command = String.format("cover -t %s %s -p *! --hold no --drop harddrop", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(90, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(60, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(150, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

            assertThat(csv.size()).isEqualTo(5040);
            assertThat(csv.row("name", "ISLJZOT"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "O");

            assertThat(csv.row("name", "OILSZJT"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "X");

            assertThat(csv.row("name", "ZTJLSIO"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X");
        }

        @Test
        void case1Page1() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ#1:6";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ#1:-1";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            String fumen1d = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2d = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1680, all, fumen1d));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2240, all, fumen2d));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3108, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(812, all));
        }

        @Test
        void case1Page2() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ#:";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ#1:";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            String fumen1d = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2d = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1680, all, fumen1d));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2240, all, fumen2d));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3108, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(812, all));
        }

        @Test
        void case1Page3() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ#";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ#:-1";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            String fumen1d = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2d = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1680, all, fumen1d));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2240, all, fumen2d));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3108, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(812, all));
        }

        @Test
        void case1Short() throws Exception {
            String fumen1 = "v115@vhFRQJUGJKJJvMJTNJGBJ#1:5";
            String fumen2 = "v115@vhFRQJPGJKJJGMJTNJ0BJ#1:5";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            String fumen1d = "v115@vhFRQJUGJKJJvMJTNJGBJ";
            String fumen2d = "v115@vhFRQJPGJKJJGMJTNJ0BJ";

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(960, all, fumen1d));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(960, all, fumen2d));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1500, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(420, all));
        }

        @Test
        void case2() throws Exception {
            String fumen = "v115@vhGRQJWLJSBJTyIXoIVjIUUI";

            String command = String.format("cover -t %s -p *!", fumen);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(64, all, fumen));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(64, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(64, all));
        }

        @Test
        void case3() throws Exception {
            String fumen1 = "http://fumen.zui.jp/?v115@vhGTJJSQJJHJWSJUIJXGJVBJ";
            String fumen2 = "v115@vhGJHJqMJvNJ+LJsKJVBJTJJ";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            String fumen1d = "v115@vhGTJJSQJJHJWSJUIJXGJVBJ";

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2520, all, fumen1d));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3822, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(4102, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(2240, all));
        }

        @Test
        void case4() throws Exception {
            // 2連パフェ OLZT
            String fumen1 = "v115@JhA8GeE8BeA8BeI8KeXHJvhEaIJT/I0AJlBJpIJ";
            String fumen2 = "v115@JhA8GeE8BeA8BeI8Ke0AJvhET/I6GJOHJdIJpIJ";

            String command = String.format("cover -t %s %s -p *!", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3840, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3780, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(5040, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(2580, all));
        }

        @Test
        void case4_7p6() throws Exception {
            // 2連パフェ OLZT
            String fumen1 = "v115@JhA8GeE8BeA8BeI8KeXHJvhEaIJT/I0AJlBJpIJ";
            String fumen2 = "v115@JhA8GeE8BeA8BeI8Ke0AJvhET/I6GJOHJdIJpIJ";

            String command = String.format("cover -t %s %s -p *p6", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(720, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(720, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1440, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
        }

        @Test
        void lessThanRequired() throws Exception {
            // パターンのミノ数が、必要なミノ数より少ない場合
            // 「途中までおけたとき成功」とするユースケースが思いつかず、間違えて入力したとき判断が難しくなるので現時点では必ず失敗とする
            String fumen1 = "v115@JhA8GeE8BeA8BeI8KeXHJvhEaIJT/I0AJlBJpIJ";
            String fumen2 = "v115@JhA8GeE8BeA8BeI8Ke0AJvhET/I6GJOHJdIJpIJ";

            String command = String.format("cover -t %s %s -p *p5", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 2520;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(0, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(0, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(0, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
        }

        @Test
        void case5() throws Exception {
            String fumen1 = "v115@vhERQJKJJUGJvMJTNJ";

            String command = String.format("cover -t %s -p *p7 --drop harddrop", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(720, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(720, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(720, all));
        }

        @Test
        void case5Mirror() throws Exception {
            {
                String fumen1 = "v115@vhERQJKJJUGJvMJTNJ";
                String fumen2 = "v115@vhERQJ+NJ3GJMKJTJJ";

                String command = String.format("cover -t %s %s -p *p7", fumen1, fumen2);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundSolutions(960, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundSolutions(960, all, fumen2));
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1536, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(384, all));
            }

            {
                String fumen1 = "v115@vhERQJKJJUGJvMJTNJ";

                String command = String.format("cover -t %s -p *p7 --mirror yes", fumen1);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundSolutions(960, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(960, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1536, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(384, all));
            }
        }

        @Test
        void case6Right() throws Exception {
            String fumen1 = "v115@UhA8DeA8AeC8DeC8JeRQJ";
            String fumen2 = "v115@UhA8DeA8AeC8DeC8JeBQJ";

            String command = String.format("cover -t %s %s -p I", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 1;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(1, all));
        }

        @Test
        void case6Left() throws Exception {
            String fumen1 = "v115@ShA8DeA8CeC8DeC8JeRQJ";
            String fumen2 = "v115@ShA8DeA8CeC8DeC8JeBQJ";

            String command = String.format("cover -t %s %s -p I", fumen1, fumen2);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 1;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(0, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(0, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(0, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
        }

        @Test
        void case7Normal() throws Exception {
            String fumen1 = "v115@vhGRQJUGJKJJvMJTNJ+DJFKJ";

            String command = String.format("cover -t %s -p *! --drop t-softdrop", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1944, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1944, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(1944, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1));

            assertThat(csv.row("name", "SLOZJIT"))
                    .containsEntry(fumen1, "X");
        }

        @Test
        void case7B2B() throws Exception {
            String fumen1 = "v115@vhGRQJUGJKJJvMJTNJ+DJFKJ";

            String command = String.format("cover -t %s -p *! --mode b2b", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2184, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(2184, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(2184, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1));

            assertThat(csv.row("name", "SLOZJIT"))
                    .containsEntry(fumen1, "O");
        }

        @Test
        void case7TSD() throws Exception {
            String fumen1 = "v115@vhGRQJUGJKJJvMJTNJ+DJFKJ";

            String command = String.format("cover -t %s -p *! --mode tsd", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2184, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(2184, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(2184, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1));

            assertThat(csv.row("name", "SLOZJIT"))
                    .containsEntry(fumen1, "O");
        }

        @Test
        void case8BT_Cycle1() throws Exception {
            // BT砲 1巡目
            String fumen1 = "v115@vhGyOJTEJm/IRQJVLJUNJvIJ";
            String fumen2 = "v115@vhGUNJvIJRQJVLJ2OJzEJi/I";
            String fumen3 = "v115@vhGUNJvIJ1QJyKJxOJTEJm/I";
            String fumen4 = "v115@vhGUNJvIJ1QJWPJyKJz/IJEJ";

            String command = String.format("cover -t %s %s %s %s -p *! --mirror yes --mode normal", fumen1, fumen2, fumen3, fumen4);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(1792, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(1792, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2088, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(2088, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(2056, all, fumen3));
            assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(2056, all, fumen3));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3248, all, fumen4));
            assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(3248, all, fumen4));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(5040, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(128, all));
        }

        @Test
        void case8BT_Cycle2() throws Exception {
            // BT砲 2巡目
            String fumen1 = "v115@9gC8EeA8AeC8AeA8CeH8AeJ8AeB8Jep5IvhFa5I38I?uAJM2IT1IFHJ";
            String fumen2 = "v115@9gC8EeA8AeC8AeA8CeH8AeJ8AeB8Jev+IvhFG0ICBJ?R8IM2IT1IFHJ";

            {
                String command = String.format("cover -t %s %s -p *! --mode b2b", fumen1, fumen2);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundSolutions(3780, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundSolutions(2688, all, fumen2));
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(4550, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(1918, all));

                // CSV
                CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

                assertThat(csv.row("name", "OTILSZJ"))
                        .containsEntry(fumen1, "X")
                        .containsEntry(fumen2, "X");
                assertThat(csv.row("name", "OTIZSJL"))
                        .containsEntry(fumen1, "X")
                        .containsEntry(fumen2, "X");
            }
            {
                String command = String.format("cover -t %s %s -p *! --mode tsd", fumen1, fumen2);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundSolutions(3780, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundSolutions(2688, all, fumen2));
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(4550, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(1918, all));

                // CSV
                CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

                assertThat(csv.row("name", "OTILSZJ"))
                        .containsEntry(fumen1, "X")
                        .containsEntry(fumen2, "X");
                assertThat(csv.row("name", "OTIZSJL"))
                        .containsEntry(fumen1, "X")
                        .containsEntry(fumen2, "X");
            }
            {
                String command = String.format("cover -t %s %s -p *! --mode any", fumen1, fumen2);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundSolutions(4284, all, fumen1));
                assertThat(log.getOutput()).contains(Messages.foundSolutions(2688, all, fumen2));
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(4606, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(2366, all));

                // CSV
                CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2));

                assertThat(csv.row("name", "OTILSZJ"))
                        .containsEntry(fumen1, "O")
                        .containsEntry(fumen2, "X");
                assertThat(csv.row("name", "OTIZSJL"))
                        .containsEntry(fumen1, "X")
                        .containsEntry(fumen2, "X");
            }
        }

        @Test
        void case9_Cycle1() throws Exception {
            // ガムシロ積み 1巡目
            String fumen1 = "v115@vhG2OJvEJULJdMJpHJTNJKDJ";

            String command = String.format("cover -t %s -p *! --mirror yes --mode normal", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(4200, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundMirrorSolutions(4200, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(5040, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(3360, all));
        }

        @Test
        void case9_Cycle2() throws Exception {
            // ガムシロ積み 2巡目
            String fumen1 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+ITvINFJ";
            String fumen2 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+IzrINFJ";
            String fumen3 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+ITzINFJ";
            String fumen4 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFUBJp5I?X9IzzIqpINFJ";

            String command = String.format("cover -t %s %s %s %s -p *! --mode tst", fumen1, fumen2, fumen3, fumen4);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3360, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3360, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3360, all, fumen3));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3600, all, fumen4));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(5040, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(1248, all));
        }

        @Test
        void case9_Cycle3() throws Exception {
            // ガムシロ積み 3巡目
            String fumens = "v115@zgA8GeC8GeE8EeD8DeG8AeF8Je5FYZAFLDmClcJSAV?DEHBEooRBJoAVBvHkPCsAAAAvhETnBMrBfqBmlBCsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeWLYZAFLDmClcJSAV?DEHBEooRBKoAVBvHUWC0AAAAvhETiBUhBflBCnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVB6vTWC0AAAAvhEMmBulBflBCnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je9KYZAFLDmClcJSAV?DEHBEooRBUoAVBKuytC6AAAAvhE+nBRmBzfB3mBUrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeUGYZAFLDmClcJSAV?DEHBEooRBaoAVBvvTWCpAAAAvhETiBGhBflB6qBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JezFYZAFLDmClcJSAV?DEHBEooRBPoAVBMdNPC0AAAAvhECnBflBRrBGnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeRGYZAFLDmClcJSAV?DEHBEooRBJoAVBqHUxCsAAAAvhEGiBsqBfqBllBCsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeRGYZAFLDmClcJSAV?DEHBEooRBJoAVBK3TWC0AAAAvhEGiBzlBflBCnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBq+TWCpAAAAvhEGmBFmBflB6qBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je5FYZAFLDmClcJSAV?DEHBEooRBJoAVBMnLuCqAAAAvhECsBTiBMmBfqBmqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVBviLuCpAAAAvhEzmB6nBUrBfqBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBKNOMCsAAAAvhEGmB/qBFrBxrBCqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeSLYZAFLDmClcJSAV?DEHBEooRBMoAVBa3jPC0AAAAvhEUiBzgBflBGnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je9KYZAFLDmClcJSAV?DEHBEooRBUoAVB6vbgCpAAAAvhEUmB+nB3rBzpBRwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JetKYZAFLDmClcJSAV?DEHBEooRBUoAVBpybgC6AAAAvhExrB6rBfqBTsB0qB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBK+TFD0AAAAvhEGmBRlBCiBsqBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeUGYZAFLDmClcJSAV?DEHBEooRBaoAVBvintCpAAAAvhEzhB6iBuqBfqBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVBs+bgCpAAAAvhECsBFrBfqBTsBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBqHUWC0AAAAvhEGmBsqBfqBCsBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeGGYZAFLDmClcJSAV?DEHBEooRBKoAVBzSNFD0AAAAvhEfiB6iBRlBsqBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBUt3LCsAAAAvhEFmB/qBGrBxrBCqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVB6C+tCpAAAAvhEsrB6nBFrBfqBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBqHUWCpAAAAvhE+mBslBflB6qBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVB6iHgCzAAAAvhEsrB6sBWrBzkBXrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVB0vTWCpAAAAvhEFmBGmBflB6qBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je1KYZAFLDmClcJSAV?DEHBEooRBUoAVBMnzPCpAAAAvhECnBTiBMmBGqBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je9KYZAFLDmClcJSAV?DEHBEooRBUoAVBKtrgCpAAAAvhE+nB3mBUrBzpBRwB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je3FYZAFLDmClcJSAV?DEHBEooRBToAVBs3HgCpAAAAvhECnBMmBGqBTsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBziPFDpAAAAvhE/rBSsBGqBUsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeSLYZAFLDmClcJSAV?DEHBEooRBMoAVBPNmPCpAAAAvhETiBXhBFlBGqBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBUNUPCvAAAAvhEVrBfnB6nBuqBzpB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBUtrgCqAAAAvhEtqBXsBUmBzkBGsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeGGYZAFLDmClcJSAV?DEHBEooRBKoAVBzijxCpAAAAvhEfiB6iBsqBFqBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBaNMgCqAAAAvhEUmBfnB6nBzfBuqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVBsXegCpAAAAvhECsBMrBFqBTsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBqybgC6AAAAvhEuqB6sBfsBzkBUrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVB0HbPCsAAAAvhEFmBUiBzfBuqBSxB v115@zgA8GeC8GeE8EeD8DeG8AeF8JesKYZAFLDmClcJSAV?DEHBEooRBaoAVBvi/wCpAAAAvhETnB6rBGlBFqBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je9KYZAFLDmClcJSAV?DEHBEooRBUoAVBKuaFDzAAAAvhE+nBRmBzfBUmB3rB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVB6vKWCzAAAAvhEsqBGsBzkBCmBXsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeRGYZAFLDmClcJSAV?DEHBEooRBJoAVBsuntC6AAAAvhE6iB1mBWlB/qBUrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBqXWWCpAAAAvhEGmBsqBFqBCsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je3FYZAFLDmClcJSAV?DEHBEooRBToAVBMn/wCpAAAAvhESnBTmBGqBFsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeFGYZAFLDmClcJSAV?DEHBEooRBUoAVBKtrgCsAAAAvhE+iB3lBsmBzkBCrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBMtzPCpAAAAvhE6mBfmBUlBGqBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBPt/VC0AAAAvhETnBXmBWlBCnBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeCLYZAFLDmClcJSAV?DEHBEooRBMoAVBKXltC0AAAAvhEWsBTiBRlBXmBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBMnzPCpAAAAvhECsBTnBMrBGqBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je1KYZAFLDmClcJSAV?DEHBEooRBUoAVBPt/VCpAAAAvhETiB/mBGqBCsBRvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBz3/VC0AAAAvhEXmBUiBWlBSsBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBKNOMCsAAAAvhE+mB3lBlqBxrBCqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeUGYZAFLDmClcJSAV?DEHBEooRBaoAVBvPltCsAAAAvhETiBGhBxhB/qBCqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBqybgC0AAAAvhEuqBSxBXnBzkBFrB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeCLYZAFLDmClcJSAV?DEHBEooRBMoAVBqnltC0AAAAvhE+nBsmBRlBXmBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeCLYZAFLDmClcJSAV?DEHBEooRBMoAVBvfLuCpAAAAvhEzlB+nBsmBfqBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je3FYZAFLDmClcJSAV?DEHBEooRBToAVBsHbPC0AAAAvhECnBMmBTnBWqBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeSHYZAFLDmClcJSAV?DEHBEooRBMoAVBzXmPCpAAAAvhEfmBUlBlmBGqBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8Je3GYZAFLDmClcJSAV?DEHBEooRBToAVBqibMC0AAAAvhEuqB6nB0mBRqBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBz/TFD0AAAAvhE/mBGgBCiBsqBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JesKYZAFLDmClcJSAV?DEHBEooRBaoAVBpvKWC0AAAAvhExmBGlBTnB6rBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBziHgC6AAAAvhE/rBSsBuqBzkBUsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JexKYZAFLDmClcJSAV?DEHBEooRBJoAVBMtHgC0AAAAvhESsBXmBuqBzpBFsB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeTCYZAFLDmClcJSAV?DEHBEooRBPoAVBpyjPC0AAAAvhERgBCdB/mBGgBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBaNUPCpAAAAvhEsrBXrB6sBWvBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeWLYZAFLDmClcJSAV?DEHBEooRBKoAVBJ9KWC0AAAAvhExmBflBTnBSrBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVBMdFgC0AAAAvhESsBXnBRqBTrBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JesKYZAFLDmClcJSAV?DEHBEooRBaoAVBPt/VC0AAAAvhETnB/mBGlBCsBFqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBM9aPCpAAAAvhECsBXrBTsBWvBxvB v115@zgA8GeC8GeE8EeD8DeG8AeF8JeuKYZAFLDmClcJSAV?DEHBEooRBKoAVBsHcgC0AAAAvhECsBMrBfqBTsBlqB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBviPFDpAAAAvhETnB6rBWqB0qBxwB v115@zgA8GeC8GeE8EeD8DeG8AeF8JelKYZAFLDmClcJSAV?DEHBEooRBUoAVBPt/VCpAAAAvhETnB/rBGqBCsBRvB";

            {
                String command = String.format("cover -t %s -p *! --mode normal", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(4994, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
            {
                String command = String.format("cover -t %s -p *! --mode any", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3232, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
            {
                String command = String.format("cover -t %s -p *! --mode tss", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3124, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
            {
                String command = String.format("cover -t %s -p *! --mode tsd", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(1764, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
            {
                String command = String.format("cover -t %s -p *! --mode tst", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(0, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
            {
                String command = String.format("cover -t %s -p *! --mode b2b", fumens);
                Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

                // Log
                int all = 5040;
                assertThat(log.getOutput()).contains(Messages.foundOrSolutions(0, all));
                assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));
            }
        }

        @Test
        void case10Prioritized1() throws Exception {
            // ガムシロ積み 2巡目
            String fumen1 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+ITvINFJ";

            String command = String.format("cover -t %s -p *! --mode tsd -P yes", fumen1);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3864, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(3864, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(3864, all));
        }

        @Test
        void case10Prioritized2() throws Exception {
            // ガムシロ積み 2巡目
            String fumen1 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+ITvINFJ";
            String fumen2 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+IzrINFJ";
            String fumen3 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFJwIUBJ?X9I6+ITzINFJ";
            String fumen4 = "v115@7gA8AeA8FeB8AeB8DeF8AeJ8AeF8Jeu6IvhFUBJp5I?X9IzzIqpINFJ";

            String command = String.format("cover -t %s %s %s %s -p *! --mode tsd -P yes", fumen1, fumen2, fumen3, fumen4);
            Log log = RunnerHelper.runnerCatchingLog(() -> EntryPointMain.main(command.split(" ")));

            // Log: 3864+336+336+504 == 5040
            int all = 5040;
            assertThat(log.getOutput()).contains(Messages.foundSolutions(3864, all, fumen1));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(336, all, fumen2));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(336, all, fumen3));
            assertThat(log.getOutput()).contains(Messages.foundSolutions(504, all, fumen4));
            assertThat(log.getOutput()).contains(Messages.foundOrSolutions(5040, all));
            assertThat(log.getOutput()).contains(Messages.foundAndSolutions(0, all));

            // CSV
            CSVStore csv = OutputFileHelper.loadCoverCSV(Arrays.asList("name", fumen1, fumen2, fumen3, fumen4));

            assertThat(csv.row("name", "OZLSIJT"))
                    .containsEntry(fumen1, "O")
                    .containsEntry(fumen2, "X")
                    .containsEntry(fumen3, "X")
                    .containsEntry(fumen4, "X");

            assertThat(csv.row("name", "JITOLSZ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "O")
                    .containsEntry(fumen3, "X")
                    .containsEntry(fumen4, "X");

            assertThat(csv.row("name", "STIZOLJ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X")
                    .containsEntry(fumen3, "O")
                    .containsEntry(fumen4, "X");

            assertThat(csv.row("name", "ZTIOSLJ"))
                    .containsEntry(fumen1, "X")
                    .containsEntry(fumen2, "X")
                    .containsEntry(fumen3, "X")
                    .containsEntry(fumen4, "O");
        }
    }
}