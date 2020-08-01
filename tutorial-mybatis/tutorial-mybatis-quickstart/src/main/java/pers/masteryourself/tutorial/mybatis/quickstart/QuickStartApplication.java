package pers.masteryourself.tutorial.mybatis.quickstart;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pers.masteryourself.tutorial.mybatis.quickstart.domain.Employee;
import pers.masteryourself.tutorial.mybatis.quickstart.mapper.EmployeeMapper;

import java.io.IOException;
import java.io.InputStream;

/**
 * <p>description : QuickStartApplication
 *
 * <p>blog : https://blog.csdn.net/masteryourself
 *
 * @author : masteryourself
 * @version : 1.0.0
 * @date : 2020/8/1 15:26
 */
public class QuickStartApplication {

    private static SqlSessionFactory sqlSessionFactory;

    public static void main(String[] args) throws Exception {
        // 初始化 SqlSessionFactory
        initSqlSessionFactory();
        // 插入员工
        saveEmp();
        // 直接使用 SqlSession
        queryWithSqlSession();
        // 使用 Mapper 映射文件
        queryWithMapper();
    }

    private static void saveEmp() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = new Employee();
            employee.setLastName("tutorial-mybatis-quickstart");
            mapper.save(employee);
            // 手动提交事务
            sqlSession.commit();
        }
    }

    private static void queryWithSqlSession() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Employee employee = sqlSession.selectOne("pers.masteryourself.tutorial.mybatis.quickstart.mapper.EmployeeMapper.getById", 1);
            System.out.println(employee);
        }
    }

    private static void queryWithMapper() {
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        }
    }

    private static void initSqlSessionFactory() throws IOException {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
    }

}
