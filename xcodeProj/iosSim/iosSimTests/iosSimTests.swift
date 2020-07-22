//
//  iosSimTests.swift
//  iosSimTests
//
//  Created by Lilia on 14/11/2019.
//  Copyright © 2019 LA. All rights reserved.
//

import XCTest
@testable import iosSim
import sample_mpp_app

class iosSimTests: XCTestCase {

    override func setUp() {
        // Put setup code here. This method is called before the invocation of each test method in the class.
    }

    override func tearDown() {
        // Put teardown code here. This method is called after the invocation of each test method in the class.
    }

    func testExample() {
        // This is an example of a functional test case.
        // Use XCTAssert and related functions to verify your tests produce the correct results.
        let deviceInfo = GetDeviceModelKt.getFullDeviceInfo()
        XCTAssert(deviceInfo.contains("iOS"))
    }

    func testPerformanceExample() {
        // This is an example of a performance test case.
        self.measure {
            // Put the code you want to measure the time of here.
        }
    }

}
