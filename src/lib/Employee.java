package lib;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class Employee {

	private String idNumber;
	
	private int yearJoined;
	private int monthJoined;
	
	private boolean isForeigner;

	private int monthlySalary;
	private int otherMonthlyIncome;
	private int annualDeductible;
	
	private String spouseIdNumber;

	private List<String> childIdNumbers;
	private HashMap<Integer, Integer> salary = new HashMap<Integer, Integer>() {{
		put(1, 3000000);
		put(2, 5000000);
		put(3, 7000000);
	}};
	
	public Employee(String idNumber, int yearJoined, int monthJoined, boolean isForeigner) {

		this.idNumber = idNumber;
		this.yearJoined = yearJoined;
		this.monthJoined = monthJoined;
		this.isForeigner = isForeigner;
		
		childIdNumbers = new LinkedList<String>();
	}
	
	/**
	 * Fungsi untuk menentukan gaji bulanan pegawai berdasarkan grade kepegawaiannya (grade 1: 3.000.000 per bulan, grade 2: 5.000.000 per bulan, grade 3: 7.000.000 per bulan)
	 * Jika pegawai adalah warga negara asing gaji bulanan diperbesar sebanyak 50%
	 */
	
	public void setMonthlySalary(int grade) {	
		monthlySalary = salary.get(grade);
		if (isForeigner) {
			monthlySalary *= 1.5;
			return;
		}
	}
	
	public void setAnnualDeductible(int deductible) {	
		this.annualDeductible = deductible;
	}
	
	public void setAdditionalIncome(int income) {	
		this.otherMonthlyIncome = income;
	}
	
	public void setSpouseId(String spouseIdNumber) {
		this.spouseIdNumber = idNumber;
	}
	
	public void addChild(String childIdNumber) {
		childIdNumbers.add(childIdNumber);
	}
	
	public int getAnnualIncomeTax() {
		
		//Menghitung berapa lama pegawai bekerja dalam setahun ini, jika pegawai sudah bekerja dari tahun sebelumnya maka otomatis dianggap 12 bulan.
		LocalDate date = LocalDate.now();

		int monthWorkingInYear = date.getMonthValue() - monthJoined;
		if (date.getYear() > yearJoined) {
			monthWorkingInYear = 12;
			System.err.println("More than 12 month working per year");
		}

		// Menghitung pajak yang harus dibayar oleh pegawai
		int tax = 0;

		int NumberOfChild = childIdNumbers.size();
		if (NumberOfChild > 3) {
			NumberOfChild= 3;
		}
		
		//
		tax = (int) Math.round(0.05 * (((monthlySalary + otherMonthlyIncome) * monthWorkingInYear) - annualDeductible - 54000000));

		if (spouseIdNumber.equals("")) {
			tax = (int) Math.round(0.05 * (((monthlySalary + otherMonthlyIncome) * monthWorkingInYear) - annualDeductible - (54000000 + 4500000 + (NumberOfChild * 1500000))));
		}
		
		if (tax < 0) {
			return 0;
		}
		
		return tax;
	}
}
