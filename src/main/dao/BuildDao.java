package main.dao;

import main.common.R;
import main.entity.buildHelper.Graph;
import main.entity.Building;

import java.io.*;
import java.util.List;

public class BuildDao {
    private static Graph graph;
    /**
     * 从磁盘中加载图的信息，然后把它放入BaseContext中
     */
    public void load() throws IOException, ClassNotFoundException {
        ObjectInputStream ois = new ObjectInputStream(
                new BufferedInputStream(new FileInputStream(new File("./resources/buildings.bin")))
        );
        graph = (Graph) ois.readObject();
        ois.close();
    }

    /**
     * 将图的信息持久化到磁盘里
     */
    public void dump() throws IOException {
        ObjectOutputStream oos = new ObjectOutputStream(
                new BufferedOutputStream(new FileOutputStream(new File("./resources/buildings.bin")))
        );
        oos.writeObject(graph);
        oos.flush();
        oos.close();
    }

    public static void setGraph(Graph graph) {
        BuildDao.graph = graph;
    }

    public static Graph getGraph() {
        return graph;
    }

    /**
     * 向图中添加边，如果有边，则修改边
     * @param vex1
     * @param vex2
     * @param weight
     * @return
     */
    public R add(int vex1,int vex2,int weight){
        graph.addEdge(vex1,vex2,weight);
        return R.success("添加成功");
    }

    public Building query(int num){
        return graph.getBuild(num);
    }

    public R updateBuildMsg(int num,String newMsg){
        graph.updateMsg(num,newMsg);
        return R.success("更新成功");
    }

    public R queryMinPath(int sNum, int eNum) {
        return R.success(graph.Shortpath_Print(sNum,eNum));
    }

    public R queryAllPath(int sNum,int eNum){
        return R.success(graph.Allpath_Print(sNum,eNum));
    }

    public R queryMore(List<Integer> list){
        return graph.BestPath(list);
    }
}
