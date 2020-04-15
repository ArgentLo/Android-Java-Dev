package com.example.a4my_reportcard_java;

public class ReportCard {

    /** init variables as private */
    /**        static final (or final static): gives you a CONSTANT (can't change later)
     *         mPHYSICS = m (private var) + PHYSICS (constant should be capitalized)*/
    private static final int mPHYSICS = 1;
    private static final int mCHEMISTRY = 2;
    private static final int mMATHS = 3;

    //Term case
    private static final int mMID = 1;
    private static final int mEND = 2;
    // id
    private int mId;
    //Total marks for midterm papers
    private int mTOTAL_MID;
    //Marks for midterm marks
    private int mMarks_mid_physics;
    private int mMarks_mid_chemistry;
    private int mMarks_mid_maths;
    //Total marks for the endterm papers
    private int mTOTAL_END;
    private int mMarks_end_physics;
    private int mMarks_end_chemistry;
    private int mMarks_end_maths;

    /** Constructor for this ReportCard class */
    /**     constructor should be of same name of the class.
     *      unless we use Factory Construction MediaPlayer.create(...) */
    public ReportCard(int id, int mid_chem, int mid_maths, int mid_phy,
                      int end_chem, int end_maths, int end_phy,
                      int mid_total, int end_total){
        mId = id;
        mMarks_mid_chemistry = mid_chem;
        mMarks_mid_maths = mid_maths;
        mMarks_mid_physics = mid_phy;
        mMarks_end_maths = end_maths;
        mMarks_end_physics = end_phy;
        mMarks_end_chemistry = end_chem;
        mTOTAL_MID = mid_total;
        mTOTAL_END = end_total;
    }

    /** Setter functions */
    public void setMarks(int subject, int term, int marks) {
        switch (term){
            case mMID:
                // Check if the input marks are valid or not
                if (marks >= 0 && marks <= 50){
                    switch (subject){
                        case mCHEMISTRY:
                            mMarks_mid_chemistry = marks;
                            break;
                        case mPHYSICS:
                            mMarks_mid_physics = marks;
                            break;
                        case mMATHS:
                            mMarks_mid_maths = marks;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid subject to set marks");
                    }
                    break;
                } else {
                    throw new IllegalArgumentException("Invalid marks");
                }
            case mEND:
                // Check if the input marks are valid or not
                if (marks >= 0 && marks <= 100){
                    switch (subject) {
                        case mCHEMISTRY:
                            mMarks_end_chemistry = marks;
                            break;
                        case mPHYSICS:
                            mMarks_end_physics = marks;
                            break;
                        case mMATHS:
                            mMarks_end_maths = marks;
                            break;
                        default:
                            throw new IllegalArgumentException("Invalid subject to set marks");
                    }
                    break;
                } else {
                    throw new IllegalArgumentException("Invalid marks");
                }
            default:
                throw new IllegalArgumentException("Invalid term to set marks.");
        }
    }
    /** Getter functions */
    public int getMarks(int subject, int term, int marks){

	  switch (term){
            case mMID:
                switch (subject){
                    case mCHEMISTRY:
                        return mMarks_mid_chemistry;
                    case mPHYSICS:
                        return mMarks_mid_physics;
                    case mMATHS:
                        return mMarks_mid_maths;
                    default:
                        throw new IllegalArgumentException("Invalid subject to get marks");

                }

            case mEND:
                switch (subject) {
                    case mCHEMISTRY:
                        return mMarks_end_chemistry;
                    case mPHYSICS:
                        return mMarks_end_physics;
                    case mMATHS:
                        return mMarks_end_maths;
                    default:
                        throw new IllegalArgumentException("Invalid subject to set marks");
                }

            default:
                throw new IllegalArgumentException("Invalid term to set marks");
        }
    }

    /* Get midterm percentage */
    public double getMidPercentage(){
        return ((mMarks_mid_chemistry+mMarks_mid_physics+mMarks_mid_maths)/3*(mTOTAL_MID))*100;
    }

    /* Get endterm percentage */
    public double getEndPercentage(){
        return ((mMarks_end_chemistry+mMarks_end_physics+mMarks_end_maths)/3*(mTOTAL_END))*100;
    }

    /* Get overall percentage
     * Overall = 40% Midterm + 60% Final */
    public double getPercentage(){
        return getMidPercentage()*.4 + getEndPercentage()*.6;
    }

    @Override
    public String toString() {
        return "Reportcard \n" +
                "Id :" + mId + "\n" +
                "Total midterm marks: " + mTOTAL_MID + "\n" +
                "Midterm marks \n" +
                "Chemistry: " + mMarks_mid_chemistry + "\n" +
                "Physics: " + mMarks_mid_physics + "\n" +
                "Maths: " + mMarks_mid_maths + "\n" +
                "Midterm Percentage: " + getMidPercentage() + "\n" +
                "Total endterm marks: " + mTOTAL_END + "\n" +
                "Endterm marks \n" +
                "Chemistry: " + mMarks_end_chemistry + "\n" +
                "Physics: " + mMarks_end_physics + "\n" +
                "Maths: " + mMarks_end_maths + "\n" +
                "Endterm Percentage: " + getEndPercentage() + "\n" +
                "Overall Percentage: " + getPercentage();
    }
}
