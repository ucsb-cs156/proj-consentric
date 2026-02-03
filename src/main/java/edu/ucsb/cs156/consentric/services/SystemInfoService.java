package edu.ucsb.cs156.consentric.services;

import edu.ucsb.cs156.consentric.models.SystemInfo;

/**
 * The SystemInfoService is a service that provides global information about the system and makes it
 * available to the frontend. For details on what information is provided, see the SystemInfo class.
 *
 * @see edu.ucsb.cs156.consentric.models.SystemInfo
 */
public abstract class SystemInfoService {
  /**
   * This method returns the system information.
   *
   * @see edu.ucsb.cs156.consentric.models.SystemInfo
   * @return the system information
   */
  public abstract SystemInfo getSystemInfo();
}
