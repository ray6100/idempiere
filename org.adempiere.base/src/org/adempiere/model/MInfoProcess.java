/******************************************************************************
 * Product: iDempiere ERP & CRM Smart Business Solution                       *
 * This program is free software; you can redistribute it and/or modify it    *
 * under the terms version 2 of the GNU General Public License as published   *
 * by the Free Software Foundation. This program is distributed in the hope   *
 * that it will be useful, but WITHOUT ANY WARRANTY; without even the implied *
 * warranty of MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.           *
 * See the GNU General Public License for more details.                       *
 * You should have received a copy of the GNU General Public License along    *
 * with this program; if not, write to the Free Software Foundation, Inc.,    *
 * 59 Temple Place, Suite 330, Boston, MA 02111-1307 USA.                     *
 *****************************************************************************/

package org.adempiere.model;

import java.sql.ResultSet;
import java.util.Properties;
import java.util.logging.Level;
import org.compiere.model.MColumn;
import org.compiere.model.MProcess;
import org.compiere.model.X_AD_InfoProcess;
import org.compiere.util.Env;
import org.compiere.util.Evaluatee;
import org.compiere.util.Evaluator;

/**
 * Contain info of process in info window
 * include process_id, image name, name
 * @author hieplq
 *
 */
public class MInfoProcess extends X_AD_InfoProcess {
	/**
	 * 
	 */
	private static final long serialVersionUID = -7324387365288006121L;

	/**
	 * {@inheritDoc}
	 * @param ctx
	 * @param AD_InfoProcess_ID
	 * @param trxName
	 */
	public MInfoProcess(Properties ctx, int AD_InfoProcess_ID, String trxName) {
		super(ctx, AD_InfoProcess_ID, trxName);

	}

	/**
	 * {@inheritDoc}
	 * @param ctx
	 * @param rs
	 * @param trxName
	 */
	public MInfoProcess(Properties ctx, ResultSet rs, String trxName) {
		super(ctx, rs, trxName);

	}
	
	protected String m_viewIDName;
	
	/**************************************************************************
	 *	Is the Column Visible ? Evaluater base in display logic expression and context of this po
	 *  @return true, if visible
	 */
	public boolean isDisplayed (final int windowNo)
	{
		return isDisplayed(this.getCtx(), windowNo);
}
	
	 /**************************************************************************
	 * Is the Column Visible ? Evaluater base in display logic expression and context
	 * @param ctx
	 * @return
	 */
	public boolean isDisplayed(final Properties ctx, final int windowNo) {		
		if (getDisplayLogic() == null || getDisplayLogic().trim().length() == 0)
			return true;
		
		Evaluatee evaluatee = new Evaluatee() {
			public String get_ValueAsString(String variableName) {
				return Env.getContext (ctx, windowNo, variableName, true);
			}
		};
		
		boolean retValue = Evaluator.evaluateLogic(evaluatee, getDisplayLogic());
		if (log.isLoggable(Level.FINEST)) log.finest(MProcess.get(getCtx(), getAD_Process_ID()).getName() 
					+ " (" + getDisplayLogic() + ") => " + retValue);
		return retValue;
	}
	
	/**
	 * name of column define is ViewID
	 * @return
	 */
	public String getViewIDName (){
		// no column define
		if (getAD_Column_ID() == 0)
			return null;
		
		// return cache value
		if (m_viewIDName != null)
			return m_viewIDName;
		
		MColumn viewIDColumn = (MColumn)getAD_Column();
		if (viewIDColumn != null){
			return viewIDColumn.getColumnName();
}
		
		return null;
	}
	
}
