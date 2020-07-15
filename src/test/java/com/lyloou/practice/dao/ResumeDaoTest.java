package com.lyloou.practice.dao;

import com.lyloou.practice.model.Resume;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.persistence.criteria.*;
import java.util.List;
import java.util.Optional;

/**
 * @author lyloou
 * @date 2020/06/14
 * @desc
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:applicationContext.xml"})
public class ResumeDaoTest {
    // 要测试IOC哪个对象注⼊即可
    @Autowired
    private ResumeDao resumeDao;

    /**
     * dao层接⼝调⽤，分成两块：
     * 1、基础的增删改查
     * 2、专⻔针对查询的详细分析使⽤
     */
    @Test
    public void testFindById() {
        // 早期的版本 dao.findOne(id);
 /*
 select resume0_.id as id1_0_0_,
 resume0_.address as address2_0_0_, resume0_.name as
name3_0_0_,
 resume0_.phone as phone4_0_0_ from tb_resume resume0_
where resume0_.id=?
 */
        Optional<Resume> optional = resumeDao.findById(1l);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testFindOne() {
        Resume resume = new Resume();
        resume.setId(1l);
        resume.setName("张三");
        Example<Resume> example = Example.of(resume);
        Optional<Resume> one = resumeDao.findOne(example);
        Resume resume1 = one.get();
        System.out.println(resume1);
    }

    @Test
    public void testSave() {
        // 新增和更新都使⽤save⽅法，通过传⼊的对象的主键有⽆来区分，没有主键信息那就是新增，有主键信息就是更新
        Resume resume = new Resume();
        resume.setId(5l);
        resume.setName("赵六六");
        resume.setAddress("成都");
        resume.setPhone("132000000");
        Resume save = resumeDao.save(resume);
        System.out.println(save);
    }

    @Test
    public void testDelete() {
        resumeDao.deleteById(4l);
    }

    @Test
    public void testFindAll() {
        List<Resume> list = resumeDao.findAll();
        for (Resume resume : list) {
            System.out.println(resume);
        }
    }

    /**
     * ========================针对查询的使⽤进⾏分析=======================
     * ⽅式⼀：调⽤继承的接⼝中的⽅法 findOne(),findById()
     * ⽅式⼆：可以引⼊jpql（jpa查询语⾔）语句进⾏查询 (=====>>>> jpql 语句类似于
     * sql，只不过sql操作的是数据表和字段，jpql操作的是对象和属性，⽐如 from Resume where id=xx) hql
     * ⽅式三：可以引⼊原⽣的sql语句
     * ⽅式四：可以在接⼝中⾃定义⽅法，⽽且不必引⼊jpql或者sql语句，这种⽅式叫做⽅法命名规则查询，也就是说定义的接⼝⽅法名是按照⼀定规则形成的，那么框架就能够理解我们的意图
     * ⽅式五：动态查询
     * service层传⼊dao层的条件不确定，把service拿到条件封装成⼀个对象传递给
     * Dao层，这个对象就叫做Specification（对条件的⼀个封装）
     * <p>
     * <p>
     * // 根据条件查询单个对象
     * Optional<T> findOne(@Nullable Specification<T> var1);
     * // 根据条件查询所有
     * List<T> findAll(@Nullable Specification<T> var1);
     * // 根据条件查询并进⾏分⻚
     * Page<T> findAll(@Nullable Specification<T> var1, Pageable var2);
     * // 根据条件查询并进⾏排序
     * List<T> findAll(@Nullable Specification<T> var1, Sort var2);
     * // 根据条件统计
     * long count(@Nullable Specification<T> var1);
     * <p>
     * <p>
     * interface Specification<T>
     * >  toPredicate(Root<T> var1, CriteriaQuery<?> var2, CriteriaBuilder var3);⽤来封装查询条件的
     * >  Root:根属性（查询所需要的任何属性都可以从根对象中获取）
     * >     CriteriaQuery ⾃定义查询⽅式 ⽤不上
     * >     CriteriaBuilder 查询构造器，封装了很多的查询条件（like、=等等）
     */
    @Test
    public void testJpql() {
        List<Resume> list = resumeDao.findByJpql(1l, "张三");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    @Test
    public void testSql() {
        List<Resume> list = resumeDao.findBySql("李%", "上海%");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    @Test
    public void testMethodName() {
        List<Resume> list = resumeDao.findByNameLikeAndAddress("李%", "上海");
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    // 动态查询，查询单个对象
    @Test
    public void testSpecfication() {
        /**
         * 动态条件封装
         * 匿名内部类
         *
         * toPredicate：动态组装查询条件
         *
         * 借助于两个参数完成条件拼装，，， select * from tb_resume where
         name='张三'
         * Root: 获取需要查询的对象属性
         * CriteriaBuilder：构建查询条件，内部封装了很多查询条件（模糊查询，精
         准查询）
         *
         * 需求：根据name（指定为"张三"）查询简历
         */
        Specification<Resume> specification = (Specification<Resume>) (root, criteriaQuery, criteriaBuilder) -> {
            // 获取到name属性
            Path<Object> name = root.get("name");
            // 使⽤CriteriaBuilder针对name属性构建条件（精准查询）
            Predicate predicate = criteriaBuilder.equal(name, "张三");
            return predicate;
        };
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }

    @Test
    public void testSpecficationMultiCon() {
        /**
         * 需求：根据name（指定为"张三"）并且，address 以"北"开头（模糊匹
         配），查询简历
         */
        Specification<Resume> specification = new Specification<Resume>() {
            @Override
            public Predicate toPredicate(Root<Resume> root,
                                         CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
                // 获取到name属性
                Path<Object> name = root.get("name");
                Path<Object> address = root.get("address");
                // 条件1：使⽤CriteriaBuilder针对name属性构建条件（精准查询）
                Predicate predicate1 = criteriaBuilder.equal(name, "张三");
                // 条件2：address 以"北"开头（模糊匹配）
                Predicate predicate2 = criteriaBuilder.like(address.as(String.class), "北%");
                // 组合两个条件
                Predicate and = criteriaBuilder.and(predicate1, predicate2);
                return and;
            }
        };
        Optional<Resume> optional = resumeDao.findOne(specification);
        Resume resume = optional.get();
        System.out.println(resume);
    }


    @Test
    public void testSort() {
        Sort sort = new Sort(Sort.Direction.DESC, "id");
        List<Resume> list = resumeDao.findAll(sort);
        for (int i = 0; i < list.size(); i++) {
            Resume resume = list.get(i);
            System.out.println(resume);
        }
    }

    @Test
    public void testPage() {
        /**
         * 第⼀个参数：当前查询的⻚数，从0开始
         * 第⼆个参数：每⻚查询的数量
         */
        Pageable pageable = PageRequest.of(0, 2);
        Page<Resume> all = resumeDao.findAll(pageable);
        System.out.println(all.getContent());
    }
}