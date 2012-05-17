package mbvg

import grails.test.*

class BvgControllerTests extends ControllerUnitTestCase {
    protected void setUp() {
        super.setUp()
    }

    protected void tearDown() {
        super.tearDown()
    }

    void testSomething() {
		def bvgController = new BvgController();
		bvgController.index();
		assert true;
    }
}
