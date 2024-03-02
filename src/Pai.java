import java.util.Objects;

public class Pai {
    private int num;
    private String type;
    private boolean useFlag = false;

    public Pai(int num, String type) {
        this.num = num;
        this.type = type;
    }

    public boolean getUseFlag() {
        return useFlag;
    }

    public void setUseFlag(boolean useFlag) {
        this.useFlag = useFlag;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public void setType(String type) {
        this.type = type;
    }

    public int getNum() {
        return num;
    }

    public String getType() {
        return type;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Pai pai = (Pai) o;
        return num == pai.num && Objects.equals(type, pai.type);
    }

    @Override
    public int hashCode() {
        return Objects.hash(num, type);
    }
}
