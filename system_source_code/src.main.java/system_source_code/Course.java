package system_source_code;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map.Entry;
import java.util.Set;

public class Course {

	private int availableCapacity;

	private String courseDescription;

	private CourseFormat courseFormat;

	private Integer courseID;

	private String courseTitle;
	
	private HashMap<Person,Integer> instructors_capacity;
	
	private Set<Integer> prerequisteCourses= new HashSet<Integer>();
	
	private ArrayList<Semesters> semestersOffered;

	private int totalCapacity;
	

	public Course(Integer courseID, String courseTitle,
			ArrayList<Semesters> semestersOffered) {
		this.courseID = courseID;
		this.courseTitle = courseTitle;
		this.semestersOffered = semestersOffered;
	}

	public void addRecord(Record record) {

	}

	public int getAvailableCapacity() {
		return availableCapacity;
	}

	public String getCourseDescription() {
		return courseDescription;
	}

	public CourseFormat getCourseFormat() {
		return courseFormat;
	}

	public Integer getCourseID() {
		return courseID;
	}

	public String getCourseTitle() {
		return courseTitle;
	}

	public HashMap<Person,Integer> getInstructors_capacity() {
		return instructors_capacity;
	}

	public Set<Integer> getprerequisteCourses() {
		return prerequisteCourses;
	}

	public ArrayList<Semesters> getSemestersOffered() {
		return semestersOffered;
	}

	public int getTotalCapacity() {
		return totalCapacity;
	}

	public void setAvailableCapacity(int availableCapacity) {
		this.availableCapacity = availableCapacity;
	}

	public void setCourseDescription(String courseDescription) {
		this.courseDescription = courseDescription;
	}

	public void setCourseFormat(CourseFormat courseFormat) {
		this.courseFormat = courseFormat;
	}

	public void setCourseID(Integer courseID) {
		this.courseID = courseID;
	}

	public void setCourseTitle(String courseTitle) {
		this.courseTitle = courseTitle;
	}
	
	public void setInstructors_capacity(HashMap<Person,Integer> instructors_capacity) {
		if(getInstructors_capacity() ==  null)
		{
		this.instructors_capacity = instructors_capacity;
		}
		else
		{
			for(Entry<?, ?> e: instructors_capacity.entrySet())
			{
				if(getInstructors_capacity().containsKey(e.getKey()))
				{
					int val = getInstructors_capacity().get(e.getKey());
					getInstructors_capacity().put((Person) e.getKey(), val);
				}
				
				else
					getInstructors_capacity().put((Person)e.getKey(),(int) e.getValue());
			}
		}
		 	
		
		for(Entry<?, ?> e:instructors_capacity.entrySet())
		{
			  setTotalCapacity(getTotalCapacity() +  (int)e.getValue());
			  setAvailableCapacity(getTotalCapacity());
		}
	}
	
	

	public void setprerequisteCourses(
			Set<Integer> prerequisteCourses) {
		this.prerequisteCourses = prerequisteCourses;
	}

	public void setSemestersOffered(ArrayList<Semesters> semestersOffered) {
		this.semestersOffered = semestersOffered;
	}

	public void setTotalCapacity(int totalCapacity) {
		this.totalCapacity = totalCapacity;
	}
	
	public boolean enrollStudent(int StudentId)
	{
		if(getAvailableCapacity()>0)
		{
			setAvailableCapacity(getAvailableCapacity()-1);
			return true;
		}		
		return false;
		

		
	}
 public boolean checkIfValidRequest(int StudentId)
	{
		if(getAvailableCapacity()>0)
		{
			return true;
		}
		return false;
	}
}