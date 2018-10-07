package cn.inctech.app.biz.demo.user.action;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
@WebAppConfiguration
public class DemoUserActionTest {

	@Test
	public void testUser_insert()  throws Exception {
		String url_01="/user_insert";
		mvc.perform(MockMvcRequestBuilders.get(url_01)
				.param("userid", "tom_001").param("email", "tom@ly.com")
				.param("descr", "World").param("password", "ares")
				.param("status", "true").param("username", "little tom"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	MockMvc mvc;
    @Autowired WebApplicationContext context;

    @Before
    public void setupMockMvc() throws Exception {
        mvc = MockMvcBuilders.webAppContextSetup(context).build();
    }

}
