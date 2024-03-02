import com.sun.deploy.util.StringUtils;

import java.util.*;
import java.util.stream.Collectors;

public class main {
    public static final List<String> typeList = Arrays.asList("万", "条", "筒");
    public static final List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9);

    public static void main(String[] args) {

//        for (int i = 0; i < 100; i++) {
//            List<Pai> paiList = get14Pai();
//            String paiStr = print(paiList);
//            System.out.println(divide(paiList));
//            System.err.println();
//        }


        print(get14Paiexample("4w4w4w5w6w7w7w9w4t5t6t4T5T6T"));
        divide(get14Paiexample("4w4w4w5w6w7w7w9w4t5t6t4T5T6T"));
        System.err.println();
        print(get14Paiexample("1t2t3t9t9t9t5w7w8w8w9w2T4T6T"));
        divide(get14Paiexample("1t2t3t9t9t9t5w7w8w8w9w2T4T6T"));

    }

    private static String divide(List<Pai> paiList) {
        String divideChengPai = divideChengPai(paiList);
        String divideDuiZi = divideDuiZi(paiList);
        String divideKaZhangAndBianKan = divideKaZhangAndBianKan(paiList);
        String divideXianZhang = divideXianZhang(paiList);
        return divideChengPai + divideDuiZi + divideKaZhangAndBianKan + divideXianZhang;
    }

    private static String divideXianZhang(List<Pai> paiList) {
        String result = "闲张";
        int size = paiList.size();
        for (int i = 0; i < size; i++) {
            Pai pai = paiList.get(i);
            if (pai.getUseFlag()) {
                continue;
            }

            result += pai.getNum() + pai.getType() + ",";
            pai.setUseFlag(true);
        }
        System.err.print(result);
        return result;
    }

    private static List<Pai> get14Paiexample(String str) {
        List<Pai> paiList = new ArrayList<>();
        String paiStr = str.replace("t", "条").replace("T", "筒").replace("w", "万");
        for (int i = 0; i < 28; i = i + 2) {
            String num = paiStr.substring(i, i + 1);
            String type = paiStr.substring(i + 1, i + 2);
            Pai pai = new Pai(Integer.parseInt(num),type);
            paiList.add(pai);
        }

        return paiList;
    }

    private static String divideKaZhangAndBianKan(List<Pai> paiList) {
        String result = "";
        int size = paiList.size();
        for (int i = 0; i < size; i++) {
            Pai pai = paiList.get(i);
            if (pai.getUseFlag()) {
                continue;
            }
            pai.setUseFlag(true);
            //判断是否有双面搭
            if (pai.getNum() > 2 && pai.getNum() < 7) {
                Result shuangmiandaExist = exist(paiList, pai.getNum() + 1, pai.getType());
                if (shuangmiandaExist.getExist()) {
                    System.err.print("双面搭" + pai.getNum() + "" + (pai.getNum() + 1) + pai.getType() + ",");
                    continue;
                }
            }

            //判断是否有卡张搭
            if (pai.getNum() > 2 && pai.getNum() < 6) {
                Result kazhangdaExist = exist(paiList, pai.getNum() + 2, pai.getType());
                if (kazhangdaExist.getExist()) {
                    System.err.print("卡张搭" + pai.getNum() + "" + (pai.getNum() + 2) + pai.getType() + ",");
                    continue;
                }
            }
            //判断是否有边砍搭
            if (pai.getNum() == 1 || pai.getNum() == 7) {
                Result kazhangdaExist = exist(paiList, pai.getNum() + 2, pai.getType());
                if (kazhangdaExist.getExist()) {
                    System.err.print("边砍搭" + pai.getNum() + "" + (pai.getNum() + 2) + pai.getType() + ",");
                    continue;
                }
            }
            //判断是否有边搭
            if (pai.getNum() == 1 || pai.getNum() == 8) {
                Result kazhangdaExist = exist(paiList, pai.getNum() + 1, pai.getType());
                if (kazhangdaExist.getExist()) {
                    System.err.print("边搭" + pai.getNum() + "" + (pai.getNum() + 1) + pai.getType() + ",");
                    continue;
                }
            }


            pai.setUseFlag(false);
        }
        return result;
    }

    private static String divideDuiZi(List<Pai> paiList) {
        String result = "";
        int size = paiList.size();
        for (int i = 0; i < size; i++) {
            Pai pai = paiList.get(i);
            if (pai.getUseFlag()) {
                continue;
            }
            pai.setUseFlag(true);
            //判断是否有对子带搭子,exp566
            Result duizidaida566Exist1 = exist(paiList, pai.getNum(), pai.getType());
            Result duizidaida566Exist2 = exist(paiList, pai.getNum() - 1, pai.getType());
            if (duizidaida566Exist1.getExist() && duizidaida566Exist2.getExist()) {
                System.err.print("对子带搭" + (pai.getNum() - 1) + "" + pai.getNum() + "" + pai.getNum() + pai.getType() + ",");
                continue;
            } else {
                if (duizidaida566Exist1.getExist()) {
                    duizidaida566Exist1.getPai().setUseFlag(false);
                }
                if (duizidaida566Exist2.getExist()) {
                    duizidaida566Exist2.getPai().setUseFlag(false);
                }
            }
            //判断是否有对子带搭子,exp667
            Result duizidaida667Exist1 = exist(paiList, pai.getNum(), pai.getType());
            Result duizidaida667Exist2 = exist(paiList, pai.getNum() + 1, pai.getType());
            if (duizidaida667Exist1.getExist() && duizidaida667Exist2.getExist()) {
                System.err.print("对子带搭" + "" + pai.getNum() + "" + pai.getNum() + (pai.getNum() + 1) + pai.getType() + ",");
                continue;
            } else {
                if (duizidaida667Exist1.getExist()) {
                    duizidaida667Exist1.getPai().setUseFlag(false);
                }
                if (duizidaida667Exist2.getExist()) {
                    duizidaida667Exist2.getPai().setUseFlag(false);
                }
            }

            //判断是否有对子
            Result duiziExist = exist(paiList, pai.getNum(), pai.getType());
            if (duiziExist.getExist()) {
                System.err.print("对子" + pai.getNum() + pai.getType() + ",");
                continue;
            }

            pai.setUseFlag(false);
        }
        return result;
    }

    private static String divideChengPai(List<Pai> paiList) {
        String result = "";
        int size = paiList.size();
        for (int i = 0; i < size; i++) {
            Pai pai = paiList.get(i);
            if (pai.getUseFlag()) {
                continue;
            }
            pai.setUseFlag(true);
            //分解成牌，顺子，刻子
            //判断是否成刻子
            Result keziExist1 = exist(paiList, pai.getNum(), pai.getType());
            Result keziExist2 = exist(paiList, pai.getNum(), pai.getType());
            if (keziExist1.getExist() && keziExist2.getExist()) {
                System.err.print("刻子" + pai.getNum() + pai.getType() + ",");
                continue;
            } else {
                if (keziExist1.getExist()) {
                    keziExist1.getPai().setUseFlag(false);
                }
                if (keziExist2.getExist()) {
                    keziExist2.getPai().setUseFlag(false);
                }
            }

            Result shunziExist1 = exist(paiList, pai.getNum() + 1, pai.getType());
            Result shunziExist2 = exist(paiList, pai.getNum() + 2, pai.getType());
            if (shunziExist1.getExist() && shunziExist2.getExist()) {
                String second = String.valueOf(pai.getNum() + 1);
                String third = String.valueOf(pai.getNum() + 2);
                System.err.print("顺子" + pai.getNum() + second + third + pai.getType() + ",");
                continue;
            } else {
                if (shunziExist1.getExist()) {
                    shunziExist1.getPai().setUseFlag(false);
                }
                if (shunziExist2.getExist()) {
                    shunziExist2.getPai().setUseFlag(false);
                }
            }
            pai.setUseFlag(false);
        }
        return result;
    }

    private static Result exist(List<Pai> paiList, int num, String type) {
        Result result = new Result();
        for (Pai pai : paiList) {
            if (pai.getType().equals(type) && pai.getNum() == num && !pai.getUseFlag()) {
                pai.setUseFlag(true);
                result.setPai(pai);
                result.setExist(true);
                return result;
            }
        }
        return result;
    }


    private static String print(List<Pai> paiList) {
        String result = "";
        for (Pai pai : paiList) {
            String s = pai.getNum() + pai.getType() + " ";
            System.err.print(s);
            result += s;
        }
        System.err.println();
//        for (Pai pai : paiList) {
//            System.err.print(pai.getType()+" ");
//        }
//        System.err.println();
        return result;
    }

    private static List<Pai> get14Pai() {
        List<Pai> paiList = new ArrayList<>();
        for (int i = 0; i < 14; i++) {
            Pai pai = new Pai(numList.get(new Random().nextInt(numList.size())), typeList.get(new Random().nextInt(typeList.size())));
            int sameCount = 0;
            for (Pai pai1 : paiList) {
                if (pai1.equals(pai)) {
                    sameCount++;
                }
            }
            if (sameCount != 4) {
                paiList.add(pai);
            } else {
                i--;
            }
        }
        paiList = paiList.parallelStream().sorted(Comparator.comparing(Pai::getType).reversed().thenComparing(Pai::getNum)).collect(Collectors.toList());
        return paiList;
    }
}
