@ExtendWith(SpringExtension.class) 1
@WebMvcTest(ScheduleController.class) 2
@Log4j2
public class ScheduleControllerUnitTest {
  .....
  
	@MockBean 3
	private ScheduleService scheduleService;

	@MockBean
	private EmployeesServiceProxy proxy;

	@Autowired 4
	private MockMvc mockMvc;

	@BeforeAll 5
	public static void init() {
		defaultSchedule = new Schedule(week, employeName, assessmentDateTime, assessmentLocation);
		defaultSchedule.setId(id);
		defaultSchedule.setOccupantName(occupant);
	}
    
  	@Test
	void getRdvAssessmentTest() throws Exception {
		List<Schedule> weekAssessments = Arrays.asList(defaultSchedule);

		Mockito
			.when(this.scheduleService.getRdvAssessment(week)
			.thenReturn(weekAssessments);


		final String link = "/schedule/week/"+week;

		this.mockMvc.perform(MockMvcRequestBuilders 6
				.get(link)
				.accept(MediaType.APPLICATION_JSON_UTF8))
				.andDo(print())
				.andExpect(status().isOk()) 7
				.andExpect(jsonPath("$", hasSize(weekAssessments.size()))) 8 
				.andExpect(jsonPath("$.[0].id", is((int)weekAssessments.get(0).getId())));
	}
}
