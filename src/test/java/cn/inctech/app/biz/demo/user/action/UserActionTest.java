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

public class UserActionTest {

	/*@Test*/
	public void testUser_insert()  throws Exception {
		String url_01="/sys/user_regist";
		mvc.perform(MockMvcRequestBuilders.get(url_01)
				.param("userid", "user2").param("email", "tom@ly.com")
				.param("descr", "World").param("password", "123456")
				.param("status", "true").param("username", "user2"))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
	}
	
	@Test
	public void tes_allcode()  throws Exception {
		String url_01="/sys/sys_code/all";
		mvc.perform(MockMvcRequestBuilders.get(url_01))
		.andExpect(MockMvcResultMatchers.status().isOk())
		.andDo(MockMvcResultHandlers.print());
		url_01="/sys/sys_code/sex";
		mvc.perform(MockMvcRequestBuilders.get(url_01))
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
