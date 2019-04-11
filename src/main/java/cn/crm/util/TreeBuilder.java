package cn.crm.util;


import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONArray;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@Slf4j
public class TreeBuilder<T> {


    protected List<T> objList;
    protected Set<T> rootSet = new HashSet<>(16);
    protected Set<T> leafSet = new HashSet<>(16);

    /**
     * 是否为可以构建树的类型
     * @return true:符合条件;false:不符合条件
     * @throws NoSuchFieldException
     */
    public boolean isBuildType() throws NoSuchFieldException {
        try {
            Assert.notNull(objList, "-------->init error:objList is null!");
            Assert.notEmpty(objList, "-------->init error:there is no element in objList");
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        //若id不为Long型、parentid
        if (objList.get(0).getClass().getDeclaredField("admin_parentId") == null) {
            if (log.isDebugEnabled()) {
                log.debug("----->此类型不能构造树形结构：{}", objList.get(0).getClass().getName());
            }
            return false;
        } else {
            return true;
        }
    }

    /**
     * 获取根节点
     * @return Set<T> 根节点列表，获取失败返回null
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    private Set<T> getRoots() throws NoSuchFieldException, IllegalAccessException {
        if (!isBuildType()) {
            return null;
        }
        if (rootSet!=null) {
            this.rootSet.clear();
        }
        if(leafSet!=null){
            this.leafSet.clear();
        }
        if (this.objList.size() > 0 && this.objList != null) {
            for (T node : objList) {
                Field parentidFiled = node.getClass().getDeclaredField("admin_parentId");
                parentidFiled.setAccessible(true);

                if(parentidFiled.get(node)==null){
                    this.rootSet.add(node);
                }else{
                    this.leafSet.add(node);
                }
            }
        }
        return rootSet;
    }

    /**
     * 获取子节点,并拼装到对应父节点中
     * @param parentNode 父节点对象
     * @return Set<T> 传入节点下子节点列表，获取失败返回null
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public Set<T> getChildren(T parentNode) throws NoSuchFieldException, IllegalAccessException {
        Set<T> childrenSet = new HashSet<>(16);
        if (parentNode == null) {
            log.warn("------->parentNode is null when get children nodes!");
            return null;
        }
        for (T node : this.leafSet) {
            Field parentidFiled = node.getClass().getDeclaredField("admin_parentId");
            parentidFiled.setAccessible(true);
            Field idFiled = parentNode.getClass().getDeclaredField("admin_id");
            idFiled.setAccessible(true);
            if (parentidFiled.get(node).equals(idFiled.get(parentNode))) {
                childrenSet.add(node);
            }
        }
        return childrenSet;
    }

    /**
     * 递归子节点
     * @param node 递归起始点，若有子节点向下递归;若无子节点，退出递归
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public void buildChildNode(T node) throws NoSuchFieldException, IllegalAccessException {
        Set<T> childrenSet = this.getChildren(node);
        if (!childrenSet.isEmpty()) {
            for (T childNode : childrenSet) {
                this.buildChildNode(childNode);
            }
        }
        //node.getClass().getField("childrenSet").setAccessible(true);
        node.getClass().getField("childrenSet").set(node, childrenSet);
    }

    /**
     * 构造树型结构
     * @return List<T> 属性结构列表
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public List<T> buildTree() throws NoSuchFieldException, IllegalAccessException {
        List<T> treeList = new ArrayList<>(16);
        this.setRootSet(this.getRoots());
        for (T node : this.rootSet) {
            this.buildChildNode(node);
            treeList.add(node);
        }
        return treeList;
    }

    /**
     * 构造json树型结构
     * @return String 解析为json字符串的属性结构
     * @throws NoSuchFieldException
     * @throws IllegalAccessException
     */
    public String buildJsonTree() throws NoSuchFieldException, IllegalAccessException {
        List<T> nodeTree = this.buildTree();
        JSONArray jsonArray = JSONArray.fromObject(nodeTree);
        return jsonArray.toString();
    }

}
