package entry.cover;

import entry.DropType;
import exceptions.FinderParseException;

import java.util.Collections;
import java.util.List;

public class CoverSettings {
    private static final String DEFAULT_LOG_FILE_PATH = "output/last_output.txt";
    private static final String DEFAULT_OUTPUT_BASE_FILE_PATH = "output/cover.csv";

    private String logFilePath = DEFAULT_LOG_FILE_PATH;
    private String outputBaseFilePath = DEFAULT_OUTPUT_BASE_FILE_PATH;
    private List<String> patterns = Collections.emptyList();
    private List<CoverParameter> parameters;
    private DropType dropType = DropType.Softdrop;
    private boolean isUsingHold = true;
    private CoverModes mode = CoverModes.Normal;
    private boolean isUsingPriority = false;
    private int lastSoftdrop = 0;
    private int startingB2B = 0;

    // ********* Getter ************
    boolean isUsingHold() {
        return isUsingHold;
    }

    boolean isUsingPriority() {
        return isUsingPriority;
    }

    List<CoverParameter> getParameters() {
        return parameters;
    }

    List<String> getPatterns() {
        return patterns;
    }

    String getLogFilePath() {
        return logFilePath;
    }

    String getOutputBaseFilePath() {
        return outputBaseFilePath;
    }

    DropType getDropType() {
        return dropType;
    }

    CoverModes getCoverModes() {
        return mode;
    }

    int getLastSoftdrop() {
        return lastSoftdrop;
    }

    int getStartingB2B() {
        return startingB2B;
    }

    // ********* Setter ************
    void setUsingHold(Boolean isUsingHold) {
        this.isUsingHold = isUsingHold;
    }

    void setUsingPriority(Boolean usingPriority) {
        this.isUsingPriority = usingPriority;
    }

    void setLogFilePath(String path) {
        this.logFilePath = path;
    }

    void setOutputBaseFilePath(String path) {
        this.outputBaseFilePath = path;
    }

    void setPatterns(List<String> patterns) {
        this.patterns = patterns;
    }

    boolean isOutputToConsole() {
        return true;
    }

    public void setParameters(List<CoverParameter> parameters) {
        this.parameters = parameters;
    }

    void setDropType(String type) throws FinderParseException {
        switch (type.trim().toLowerCase()) {
            case "soft":
            case "softdrop":
                this.dropType = DropType.Softdrop;
                return;
            case "hard":
            case "harddrop":
                this.dropType = DropType.Harddrop;
                return;
            case "180":
                this.dropType = DropType.Rotation180;
                return;
            case "tsoft":
            case "tsoftdrop":
            case "t-soft":
            case "t-softdrop":
            case "t_soft":
            case "t_softdrop":
                this.dropType = DropType.SoftdropTOnly;
                return;
            case "any":
            case "any-tspin":
            case "anytspin":
            case "tspin0":
                this.dropType = DropType.AnyTSpin;
                return;
            case "tss":
            case "tspin1":
                this.dropType = DropType.TSpinSingle;
                return;
            case "tsd":
            case "tspin2":
                this.dropType = DropType.TSpinDouble;
                return;
            case "tst":
            case "tspin3":
                this.dropType = DropType.TSpinTriple;
                return;
            default:
                throw new FinderParseException("Unsupported droptype: type=" + type);
        }
    }

    void setCoverModes(String mode) throws FinderParseException {
        switch (mode.trim().toLowerCase()) {
            case "normal":
                this.mode = CoverModes.Normal;
                return;
            case "b2b":
                this.mode = CoverModes.B2BContinuous;
                return;
            case "any":
            case "any-tspin":
            case "anytspin":
            case "tspin0":
                this.mode = CoverModes.AnyTSpin;
                return;
            case "tss":
            case "tspin1":
                this.mode = CoverModes.TSpinSingle;
                return;
            case "tsd":
            case "tspin2":
                this.mode = CoverModes.TSpinDouble;
                return;
            case "tst":
            case "tspin3":
                this.mode = CoverModes.TSpinTriple;
                return;
            case "tetris":
                this.mode = CoverModes.Tetris;
                return;
            case "tetris-end":
            case "tetrisend":
                this.mode = CoverModes.TetrisEnd;
                return;
            default:
                throw new FinderParseException("Unsupported mode: mode=" + mode);
        }
    }

    public void setLastSoftdrop(int lastSoftdrop) {
        this.lastSoftdrop = lastSoftdrop;
    }

    void setStartingB2B(int startingB2B) {
        this.startingB2B = startingB2B;
    }
}
