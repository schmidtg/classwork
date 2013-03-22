package cscie160.project;

import java.io.Serializable;

/**
 * Creates permissions to perform transactions on an account (deposit, withdraw, get balance)
 */
public class AccountPermissions implements Serializable {

	private int accountNum;
    private boolean permissionDeposit;
	private boolean permissionWithdraw;
	private boolean permissionBalance;

    /**
     * Default constructor
     * @param accountNum - account number
     * @param permissionDeposit - permission (true/false) to deposit
     * @param permissionWithdraw - permission (true/false) to withdraw
     * @param permissionBalance - permission (true/false) to get balance
     */
    AccountPermissions(int accountNum, boolean permissionDeposit, boolean permissionWithdraw, boolean permissionBalance) {
		this.accountNum = accountNum;
        this.permissionDeposit = permissionDeposit;
        this.permissionWithdraw = permissionWithdraw;
        this.permissionBalance = permissionBalance;
	}

    /**
     * Gets the account number
     * @return the account number
     */
    public int getAccountNum() {
        return accountNum;
    }

    /**
     * Checks if permission to deposit is true
     * @return deposit permission
     */
    public boolean isPermissionDeposit() {
        return permissionDeposit;
    }

    /**
     * Checks if permission to withdraw is true
     * @return withdraw permission
     */
    public boolean isPermissionWithdraw() {
        return permissionWithdraw;
    }

    /**
     * Checks if permission to get balance is true
     * @return balance permission
     */
    public boolean isPermissionBalance() {
        return permissionBalance;
    }
}