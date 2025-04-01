/*!
	* Start Bootstrap - SB Admin Pro v2.0.4 (https://shop.startbootstrap.com/product/sb-admin-pro)
	* Copyright 2013-2022 Start Bootstrap
	* Licensed under SEE_LICENSE (https://github.com/StartBootstrap/sb-admin-pro/blob/master/LICENSE)
	*/
window.addEventListener('DOMContentLoaded', event => {
	// Activate feather
	feather.replace();

	// Enable tooltips globally
	var tooltipTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="tooltip"]'));
	var tooltipList = tooltipTriggerList.map(function(tooltipTriggerEl) {
		return new bootstrap.Tooltip(tooltipTriggerEl);
	});

	// Enable popovers globally
	var popoverTriggerList = [].slice.call(document.querySelectorAll('[data-bs-toggle="popover"]'));
	var popoverList = popoverTriggerList.map(function(popoverTriggerEl) {
		return new bootstrap.Popover(popoverTriggerEl);
	});

	// Activate Bootstrap scrollspy for the sticky nav component
	const stickyNav = document.body.querySelector('#stickyNav');
	if (stickyNav) {
		new bootstrap.ScrollSpy(document.body, {
			target: '#stickyNav',
			offset: 82,
		});
	}

	// 사이드바 토글
	window.addEventListener('DOMContentLoaded', event => {
		// 사이드바 토글
		const sidebarToggle = document.body.querySelector('#sidebarToggle');
		if (sidebarToggle) {
			// 페이지 로드 시 사이드바가 닫힌 상태로 시작
			if (localStorage.getItem('sb|sidebar-toggle') === 'true') {
				document.body.classList.add('sidenav-toggled');
			}

			// 클릭 시 사이드바 토글
			sidebarToggle.addEventListener('click', event => {
				event.preventDefault();
				document.body.classList.toggle('sidenav-toggled');
				// 로컬스토리지에 사이드바 상태 저장
				localStorage.setItem('sb|sidebar-toggle', document.body.classList.contains('sidenav-toggled'));
			});
		}

		// 사이드바가 LG 이하 화면에서 클릭 시 닫히도록 처리
		const sidenavContent = document.body.querySelector('#layoutSidenav_content');
		if (sidenavContent) {
			sidenavContent.addEventListener('click', event => {
				const BOOTSTRAP_LG_WIDTH = 992;
				if (window.innerWidth < BOOTSTRAP_LG_WIDTH) {
					if (document.body.classList.contains("sidenav-toggled")) {
						document.body.classList.remove("sidenav-toggled");
					}
				}
			});
		}

		// 사이드바 링크 활성화 상태 추가
		let activatedPath = window.location.pathname.match(/([\w-]+\.html)/, '$1');

		if (activatedPath) {
			activatedPath = activatedPath[0];
		} else {
			activatedPath = 'index.html';
		}

		const targetAnchors = document.body.querySelectorAll('[href="' + activatedPath + '"].nav-link');

		targetAnchors.forEach(targetAnchor => {
			let parentNode = targetAnchor.parentNode;
			while (parentNode !== null && parentNode !== document.documentElement) {
				if (parentNode.classList.contains('collapse')) {
					parentNode.classList.add('show');
					const parentNavLink = document.body.querySelector(
						'[data-bs-target="#' + parentNode.id + '"]'
					);
					parentNavLink.classList.remove('collapsed');
					parentNavLink.classList.add('active');
				}
				parentNode = parentNode.parentNode;
			}
			targetAnchor.classList.add('active');
		});

	});
});