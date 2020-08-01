package pers.masteryourself.tutorial.mybatis.quickstart;

import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import pers.masteryourself.tutorial.mybatis.quickstart.domain.Employee;
import pers.masteryourself.tutorial.mybatis.quickstart.mapper.EmployeeMapper;

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

    public static void main(String[] args) throws Exception {
        String resource = "mybatis-config.xml";
        InputStream inputStream = Resources.getResourceAsStream(resource);
        SqlSessionFactory sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
        // 使用方式一
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            Employee employee = sqlSession.selectOne("pers.masteryourself.tutorial.mybatis.quickstart.mapper.EmployeeMapper.getById", 1);
            System.out.println(employee);
        }
        // 使用方式二
        try (SqlSession sqlSession = sqlSessionFactory.openSession()) {
            EmployeeMapper mapper = sqlSession.getMapper(EmployeeMapper.class);
            Employee employee = mapper.getById(1);
            System.out.println(mapper.getClass());
            System.out.println(employee);
        }
    }

}
